package com.example.monni.ui

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.entity.Register
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.data.local.storage.DataStorage
import com.example.monni.databinding.FragmentNewRegisterDialogBinding
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Module
@AndroidEntryPoint
@InstallIn(FragmentComponent::class)
class NewRegisterDialogFragment(
    private val category: String,
    private val idRegister: Int,
    private val id: String
) : DialogFragment() {
    private lateinit var binding: FragmentNewRegisterDialogBinding
    private lateinit var dataStore: DataStorage
    private var amount: Double = 0.0
    private lateinit var date: String
    private lateinit var description: String
    private lateinit var categoryDB: CategoryDatabase
    private var categoryAmount: Double = 0.0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentNewRegisterDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dataStore = DataStorage(requireContext())
        categoryDB = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()

        CoroutineScope(Dispatchers.IO).launch {
            categoryAmount = categoryDB.categoryDao().getCategory(id, category).amount
        }

        setListeners()

        return dialog
    }



    private fun setListeners() {
        binding.apply {
            saveButtonNewRegisterDialog.setOnClickListener{
                var flag = mutableListOf(true, true, true)
                description = binding.descriptionInputDialog.editText!!.text.toString()
                date = binding.dateInputDialog.editText!!.text.toString()
                amount = binding.amountToAdd.editText!!.text.toString().toDouble()

                if(amount <= 0){
                    flag[2] = false
                    binding.amountTextNewRegisterDialogError.visibility = View.VISIBLE
                    binding.amountTextNewRegisterDialogError.text = getString(R.string.cantidad_invalida)
                } else {
                    binding.descriptionTextNewRegisterDialogError.visibility = View.GONE
                    flag[2] = true
                }
                if(description.length > 30){
                    flag[0] = false
                    binding.descriptionTextNewRegisterDialogError.visibility = View.VISIBLE
                    binding.descriptionTextNewRegisterDialogError.text = getString(R.string.mucho_texto)
                } else {
                    flag[0] = true
                    binding.descriptionTextNewRegisterDialogError.visibility = View.GONE
                }
                try{
                    if(date.length == 10){
                        var tempDate: LocalDate = LocalDate.of(date.substring(6).toInt(),
                            date.substring(3,5).toInt(),
                            date.substring(0,2).toInt())
                        println((ChronoUnit.DAYS.between(LocalDate.now(), tempDate)))
                        if(ChronoUnit.DAYS.between(LocalDate.now(), tempDate) >= 1){
                            flag[1] = false
                            binding.dateTextNewRegisterDialogError.visibility = View.VISIBLE
                            binding.dateTextNewRegisterDialogError.text = getString(R.string.fecha_invalida)
                        } else {
                            flag[1] = true
                        }
                    } else throw IOException()

                } catch (e: Exception){
                    flag[1] = false
                    binding.dateTextNewRegisterDialogError.visibility = View.VISIBLE
                    binding.dateTextNewRegisterDialogError.text = getString(R.string.fecha_invalida)
                }

                if(flag[0] && flag[1] && flag[2]){
                    CoroutineScope(Dispatchers.IO).launch {
                        categoryDB.registerDao().insert(
                            register = Register(
                                id = id,
                                category = category,
                                amount = amount,
                                date = date,
                                description = description
                            )
                        )
                        var categorytemp = categoryDB.categoryDao().getCategory(id, category)
                        categoryDB.categoryDao().updateAmount(categorytemp.amount + amount, id, category)
                    }
                    dismiss()
                }


            }
            cancelButtonNewRegisterDialog.setOnClickListener{
                dismiss()
            }
        }
    }
}