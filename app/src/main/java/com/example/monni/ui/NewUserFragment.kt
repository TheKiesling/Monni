package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.entity.Category
import com.example.monni.data.local.entity.User
import com.example.monni.data.local.source.CategoryDatabase
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
    private lateinit var user: User
    private lateinit var dataStore: DataStorage
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String
    private lateinit var categoryDatabase: CategoryDatabase

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

        categoryDatabase = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()

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
<<<<<<< Updated upstream
                    val userId = authRepository.signInWithEmailAndPassword(email, password)

=======
                    val userId = authRepository.createUserWithEmailAndPassword(email, password)
                    user = User(email,300.00,0.00)
>>>>>>> Stashed changes
                    if (userId != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveKeyValue("email", email)
                            dataStore.saveKeyValue("name", name)
                            categoryDatabase.userDao().insert(user)
                        }
                        val action = NewUserFragmentDirections.actionNewUserFragmentToHomeFragment(email)
                        requireView().findNavController().navigate(action)
                    } else {
                        Toast.makeText(requireContext(), "jk", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


    private fun createCategories() {
        val names = listOf(
            getString(R.string.actividades_recreativas),
            getString(R.string.gastos_fijos),
            getString(R.string.transporte),
            getString(R.string.comida),
            getString(R.string.vestimenta),
            getString(R.string.supermercado),
            getString(R.string.emergencia)
        )

        val colors = listOf(
            getString(R.string.shampoo),
            getString(R.string.brilliant_lavender),
            getString(R.string.light_fuchsia_pink),
            getString(R.string.lavender_blue),
            getString(R.string.maximum_blue_purple_night),
            getString(R.string.light_cobalt_blue),
            getString(R.string.ube)
        )
        var i = 0
        for (name in names) {
            CoroutineScope(Dispatchers.IO).launch {
                categoryDatabase.categoryDao().insert(
                    listOf(
                        Category(
                            id = email,
                            amount = 0.0,
                            color = colors[i],
                            name = names[i],
                            limit = 1000.0
                        )
                    )
                )
                i++
            }
        }
    }
}