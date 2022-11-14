package com.example.monni.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.monni.R
import com.example.monni.data.local.entity.Register
import com.example.monni.data.remote.firestore.FirestoreRegisterApiImpl
import com.example.monni.data.repository.register.RegisterRepository
import com.example.monni.data.repository.register.RegisterRepositoryImpl
import com.example.monni.databinding.FragmentNewRegisterDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate


class NewRegisterDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewRegisterDialogBinding
    private lateinit var repository: RegisterRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewRegisterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = RegisterRepositoryImpl(
            FirestoreRegisterApiImpl(Firebase.firestore)
        )
        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            saveButtonNewRegisterDialog.setOnClickListener {
                val amount = binding.amountToAddText.text.toString().toDouble()
                val date = LocalDate.parse(binding.dateTextDialog.text.toString())
                val desc = binding.descriptionTextNewRegisterDialog.text.toString()

                lifecycleScope.launch(Dispatchers.IO){
                    repository.createRegister(
                        register = Register(
                            amount = amount,
                            date = date,
                            desc = desc
                        )
                    )
                }

                requireView().findNavController().navigate(R.id.action_newRegisterDialogFragment_to_categoryFragment)
            }
            cancelButtonNewRegisterDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_newRegisterDialogFragment_to_categoryFragment)
            }
        }
    }
}