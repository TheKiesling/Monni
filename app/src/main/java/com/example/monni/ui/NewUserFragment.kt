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
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.entity.Category
import com.example.monni.data.local.entity.User
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.data.local.storage.DataStorage
import com.example.monni.databinding.FragmentNewUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewUserFragment : Fragment(R.layout.fragment_new_user) {
    private lateinit var binding: FragmentNewUserBinding
    private val viewModel: NewUserFragmentViewModel by viewModels()
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

        setObservables()

        setListeners()
    }

    private fun setObservables(){
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { state ->
                when(state){
                    is LoginFragmentUiState.Success -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            user = User(email, 300.0, 0.0)
                            dataStore.saveKeyValue("email", email)
                            dataStore.saveKeyValue("name", name)
                            categoryDatabase.userDao().insert(user)
                        }
                        createCategories()
                        val action = NewUserFragmentDirections.actionNewUserFragmentToHomeFragment(email)
                        requireView().findNavController().navigate(action)
                    }

                    is LoginFragmentUiState.Error ->{
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            buttonLoginFragmentLogin.setOnClickListener {
                email = binding.inputLayoutNewUserFragmentEmail.editText!!.text.toString()
                password = binding.inputLayoutNewUserFragmentPassword.editText!!.text.toString()
                name = binding.inputLayoutNewUserFragmentUsername.editText!!.text.toString()

                viewModel.createAccount(email, password)
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
        var i = -1
        for (name in names) {
            i++
            var category = Category(
                id = email,
                amount = 0.0,
                color = colors[i],
                name = name,
                limit = 1000.0
            )
            CoroutineScope(Dispatchers.IO).launch {
                categoryDatabase.categoryDao().insert(
                    listOf(
                        category
                    )
                )
            }

        }
    }
}