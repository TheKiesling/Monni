package com.example.monni.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.monni.databinding.FragmentGoalDialogBinding
import com.example.monni.databinding.FragmentNotificationDialogBinding

class NotificationDialogFragment(): DialogFragment() {
    private lateinit var binding :FragmentNotificationDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentNotificationDialogBinding.inflate(LayoutInflater.from(context))
        setListeners()
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    private fun setListeners() {
        binding.apply {
            saveButtonNotificationDialog.setOnClickListener{
                dismiss()
            }
            cancelButtonNotificationDialog.setOnClickListener{
                dismiss()
            }
        }
    }

}