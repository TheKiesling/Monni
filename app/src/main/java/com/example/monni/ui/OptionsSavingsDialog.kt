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
import com.example.monni.databinding.FragmentCategoryDialogBinding
import com.example.monni.databinding.FragmentOptionsSavingsDialogBinding

class OptionsSavingsDialog (private val email:String): DialogFragment() {
    private lateinit var binding: FragmentOptionsSavingsDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentOptionsSavingsDialogBinding.inflate(LayoutInflater.from(context))
        setListeners()
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun setListeners() {
        binding.apply {
            modifyGoalText.setOnClickListener {
                GoalDialogFragment(email).show(parentFragmentManager, "dialog")
                dismiss()
            }
            addSavingsText.setOnClickListener {
                AddSavingsDialog(email).show(parentFragmentManager, "dialog")
                dismiss()
            }
        }
    }
}