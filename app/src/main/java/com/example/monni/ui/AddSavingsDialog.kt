package com.example.monni.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.databinding.FragmentAddSavingsDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddSavingsDialog (private val email:String) : DialogFragment(){
    private lateinit var binding: FragmentAddSavingsDialogBinding
    private lateinit var categoryDB: CategoryDatabase
    private var amount: Double = 0.0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentAddSavingsDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        categoryDB = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()

        setListeners()
        return dialog
    }
    private fun setListeners() {
        binding.apply {
            saveButtonNewSavingDialog.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    var currentSavings = categoryDB.userDao().getSavings(email)
                    var currentGoal = categoryDB.userDao().getUser(email).goal
                    amount = binding.amountToAdd.editText!!.text.toString().toDouble()

                if(amount < 0){
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                    }
                }
                else if(amount > currentGoal) {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                    }
                }

                else if(currentGoal+amount > currentGoal) {
                    categoryDB.userDao().updateLimit(email, currentGoal+(currentSavings-amount))
                    currentSavings += amount
                    categoryDB.userDao().updateSavings(email, currentSavings)
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(requireContext(), "Limite actualizado", Toast.LENGTH_LONG).show()
                    }
                    dismiss()
                }

                else {
                    currentSavings += amount
                    categoryDB.userDao().updateSavings(email, currentSavings)
                    dismiss()
                }
                }
            }
            cancelButtonNewSavingDialog.setOnClickListener {
                dismiss()
            }
        }
    }
}