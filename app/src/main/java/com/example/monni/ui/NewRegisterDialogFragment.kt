package com.example.monni.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.entity.Register
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.data.local.storage.DataStorage
import com.example.monni.databinding.FragmentNewRegisterDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class NewRegisterDialogFragment(
    private val category: String
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewRegisterDialogBinding
    private lateinit var dataStore: DataStorage
    private lateinit var id: String
    private lateinit var categoryDatabase: CategoryDatabase

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
        dataStore = DataStorage(requireContext())
        categoryDatabase = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()
        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            saveButtonNewRegisterDialog.setOnClickListener {
                val amount = binding.dateInputDialog.editText!!.text.toString().toDouble()
                val date = LocalDate.parse(binding.dateInputDialog.editText!!.text.toString())
                val description = binding.descriptionInputDialog.editText!!.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    id = dataStore.getValueFromKey("email")!!
                }

                CoroutineScope(Dispatchers.IO).launch {
                    categoryDatabase.registerDao().insert(
                        Register(
                            id = id,
                            category = category,
                            amount = amount,
                            description = description,
                            date = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
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