package com.example.monni.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import com.example.monni.R
import com.example.monni.databinding.FragmentCategoryDialogBinding
import com.example.monni.databinding.FragmentGoalDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryDialogFragment(
    private val category: String
): DialogFragment() {

    private lateinit var binding : FragmentCategoryDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentCategoryDialogBinding.inflate(LayoutInflater.from(context))
        setListeners()
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun setListeners() {
        binding.apply {
            newRegisterOptionDialog.setOnClickListener {
                NewRegisterDialogFragment(category).show(parentFragmentManager, "dialog")
            }
            modifyLimitOptionDialog.setOnClickListener {
                LimitDialogFragment(category).show(parentFragmentManager, "dialog")
            }
        }
    }

}