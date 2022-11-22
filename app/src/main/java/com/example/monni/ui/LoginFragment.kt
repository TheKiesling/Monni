package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.monni.R
import com.example.monni.data.local.storage.DataStorage
import com.example.monni.data.remote.firestore.FirestoreAuthApiImpl
import com.example.monni.data.repository.auth.AuthRepository
import com.example.monni.data.repository.auth.AuthRepositoryImpl
import com.example.monni.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuthException
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginFragmentViewModel by viewModels()
    private lateinit var dataStore: DataStorage
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = DataStorage(requireContext())


        verifyLogin()
        setObservables()
        setListeners()
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { state ->

                when(state){
                    is LoginFragmentUiState.Success -> {
                        CoroutineScope(Dispatchers.IO).launch{
                            dataStore.saveKeyValue("email", email)
                        }
                        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(email)
                        requireView().findNavController().navigate(action)
                    }

                    is LoginFragmentUiState.Error ->{
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                }


            }
        }
    }

    private fun verifyLogin() {
        CoroutineScope(Dispatchers.IO).launch {
            if(dataStore.getValueFromKey("email") != null){
                email = dataStore.getValueFromKey("email").toString()
                CoroutineScope(Dispatchers.Main).launch {
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(email)
                    requireView().findNavController().navigate(action)
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            buttonLoginFragmentLogin.setOnClickListener{
                email = binding.inputLayoutLoginFragmentUsername.editText!!.text.toString()
                password = binding.inputLayoutLoginFragmentPassword.editText!!.text.toString()
                viewModel.login(email, password)
            }

            textViewLoginFragmentCreateAccount.setOnClickListener{
                requireView().findNavController().navigate(R.id.action_loginFragment_to_newUserFragment)
            }
        }
    }
}