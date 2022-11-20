package com.example.monni.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.example.monni.data.local.entity.Notification
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.data.local.storage.DataStorage
import com.example.monni.databinding.FragmentGoalDialogBinding
import com.example.monni.databinding.FragmentNotificationDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationDialogFragment(): DialogFragment() {
    private lateinit var binding :FragmentNotificationDialogBinding
    private lateinit var dataStore: DataStorage
    private lateinit var id: String
    private lateinit var categoryDatabase: CategoryDatabase

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentNotificationDialogBinding.inflate(LayoutInflater.from(context))
        setListeners()
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataStore = DataStorage(requireContext())
        categoryDatabase = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()
    }

    private fun setListeners() {
        binding.apply {
            saveButtonNotificationDialog.setOnClickListener{
                val dateLimit = binding.textInputDateNotificationDialog.editText!!.text.toString()
                val title = binding.textInputTitleNotificationDialog.editText!!.text.toString()
                val desc = binding.textInputDescNotificationDialog.editText!!.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    id = dataStore.getValueFromKey("email")!!
                }

                CoroutineScope(Dispatchers.IO).launch {
                    categoryDatabase.notificationDao().insert(
                        Notification(
                            id = id,
                            dateLimit = dateLimit,
                            title = title,
                            desc = desc
                        )
                    )
                }
                dismiss()
            }
            cancelButtonNotificationDialog.setOnClickListener{
                dismiss()
            }
        }
    }

}