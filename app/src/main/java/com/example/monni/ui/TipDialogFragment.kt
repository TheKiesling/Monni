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
import com.example.monni.databinding.FragmentGoalDialogBinding
import com.example.monni.databinding.FragmentTipDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TipDialogFragment : DialogFragment() {
    private lateinit var binding : FragmentTipDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentTipDialogBinding.inflate(LayoutInflater.from(context))
        setListeners()
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun setListeners() {
        binding.apply {
            buttonSavingsFragmentClose.setOnClickListener{
                dismiss()
            }
        }
    }

}