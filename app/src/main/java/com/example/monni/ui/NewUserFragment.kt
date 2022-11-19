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
import com.example.monni.databinding.FragmentNewUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewUserFragment : Fragment(R.layout.fragment_new_user) {
    private lateinit var binding: FragmentNewUserBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var dataStore: DataStorage
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = DataStorage(requireContext())

        authRepository = AuthRepositoryImpl(
            authApi = FirestoreAuthApiImpl()
        )

        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            buttonLoginFragmentLogin.setOnClickListener {
                email = binding.inputLayoutNewUserFragmentEmail.editText!!.text.toString()
                password = binding.inputLayoutNewUserFragmentPassword.editText!!.text.toString()
                name = binding.inputLayoutNewUserFragmentUsername.editText!!.text.toString()

                lifecycleScope.launch {
                    val userId = authRepository.signInWithEmailAndPassword(email, password)

                    if (userId != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveKeyValue("email", email)
                            dataStore.saveKeyValue("name", name)
                        }
                        val action = NewUserFragmentDirections.actionNewUserFragmentToHomeFragment()
                        requireView().findNavController().navigate(action)
                    } else {
                        Toast.makeText(requireContext(), "jk", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}