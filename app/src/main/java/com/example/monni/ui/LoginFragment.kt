package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.monni.R
import com.example.monni.data.local.storage.DataStorage
import com.example.monni.data.remote.firestore.FirestoreAuthApiImpl
import com.example.monni.data.repository.auth.AuthRepository
import com.example.monni.data.repository.auth.AuthRepositoryImpl
import com.example.monni.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var authRepository: AuthRepository
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

        authRepository = AuthRepositoryImpl(
            authApi = FirestoreAuthApiImpl()
        )

        setListeners()
    }

    private fun verifyLogin() {
        CoroutineScope(Dispatchers.IO).launch {
            if(dataStore.getValueFromKey("email") != null){
                CoroutineScope(Dispatchers.Main).launch {
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
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

                lifecycleScope.launch{
                    val userId = authRepository.signInWithEmailAndPassword(email, password)

                    if(userId != null){
                        CoroutineScope(Dispatchers.IO).launch{
                            dataStore.saveKeyValue("email", email)
                        }
                        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        requireView().findNavController().navigate(action)
                    }
                    else{
                        Toast.makeText(requireContext(), "jk", Toast.LENGTH_LONG).show()
                    }
                }
            }

            textViewLoginFragmentCreateAccount.setOnClickListener{
                requireView().findNavController().navigate(R.id.action_loginFragment_to_newUserFragment)
            }
        }
    }
}