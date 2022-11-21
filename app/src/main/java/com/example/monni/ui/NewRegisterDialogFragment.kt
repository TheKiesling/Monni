package com.example.monni.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
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
import java.time.temporal.ChronoUnit


class NewRegisterDialogFragment(
    private val category: String,
    private val idRegister: Int?
) : DialogFragment() {
    private lateinit var binding: FragmentNewRegisterDialogBinding
    private lateinit var dataStore: DataStorage
    private var id: String = ""
    private var amount: Double = 0.0
    private lateinit var date: LocalDate
    private lateinit var description: String
    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var register: Register

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
        setInfo()
        setListeners()
    }

    private fun setInfo() {
        if (idRegister != null) {
            CoroutineScope(Dispatchers.IO).launch {
                register = categoryDatabase.registerDao().getRegisterById(idRegister)
                binding.apply {
                    amountToAdd.editText!!.setText(register.amount.toString())
                    dateInputDialog.editText!!.setText(register.date)
                    descriptionInputDialog.editText!!.setText(register.description)
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            saveButtonNewRegisterDialog.setOnClickListener {
                amount = binding.amountToAdd.editText!!.text.toString().toDouble()

                var temp = binding.dateInputDialog.editText!!.text.toString()
                date = LocalDate.of(
                    temp.substring(6).toInt(),
                    temp.substring(3, 5).toInt(), temp.substring(0, 2).toInt()
                )
                description = binding.descriptionInputDialog.editText!!.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    id = dataStore.getValueFromKey("email")!!
                }
                val newRegister = Register(
                    id = id,
                    category = category,
                    amount = amount,
                    description = description,
                    date = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                )

                if (idRegister == null)
                    CoroutineScope(Dispatchers.IO).launch {
                        categoryDatabase.registerDao().insert(newRegister)
                    }
                else {
                    val register = register.copy(
                        id = id,
                        category = category,
                        amount = amount,
                        description = description,
                        date = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        categoryDatabase.registerDao().update(register)
                    }
                }
                dismiss()
            }

            cancelButtonNewRegisterDialog.setOnClickListener {
                dismiss()
            }
        }
    }
}