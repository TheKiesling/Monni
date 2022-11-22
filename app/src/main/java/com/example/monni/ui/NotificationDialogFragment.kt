package com.example.monni.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.monni.data.local.entity.Notification
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.data.local.storage.DataStorage
import com.example.monni.databinding.FragmentNotificationDialogBinding
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Module
@AndroidEntryPoint
@InstallIn(FragmentComponent::class)
class NotificationDialogFragment(): DialogFragment() {
    private lateinit var binding :FragmentNotificationDialogBinding
    private lateinit var vm: NotificationsDialogViewModel
    private lateinit var dataStore: DataStorage
    private lateinit var id: String
    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var args: List<String>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentNotificationDialogBinding.inflate(LayoutInflater.from(context))
        setListeners()
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        vm = NotificationsDialogViewModel()

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dataStore = DataStorage(requireContext())
        categoryDatabase = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()

        return dialog
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            vm.uiState.collectLatest { state ->
                vm.returnData()
                when(state){
                    is NotificationsDialogUiState.Error -> {
                        if(state.type == 1){
                            binding.dateNotificationDialogError.visibility = View.GONE
                            binding.descNotificationDialogError.visibility = View.GONE
                            binding.titleNotificationDialogError.visibility = View.VISIBLE
                            binding.titleNotificationDialogError.setText(state.msg)
                        } else if(state.type == 2){
                            binding.titleNotificationDialogError.visibility = View.GONE
                            binding.dateNotificationDialogError.visibility = View.GONE
                            binding.descNotificationDialogError.visibility = View.VISIBLE
                            binding.descNotificationDialogError.setText(state.msg)
                        }
                        else if(state.type == 3){
                            binding.titleNotificationDialogError.visibility = View.GONE
                            binding.descNotificationDialogError.visibility = View.GONE
                            binding.dateNotificationDialogError.visibility = View.VISIBLE
                            binding.dateNotificationDialogError.setText(state.msg)
                        }

                    }

                    is NotificationsDialogUiState.Success -> {
                        binding.descNotificationDialogError.visibility = View.GONE
                        binding.titleNotificationDialogError.visibility = View.GONE
                        binding.dateNotificationDialogError.visibility = View.GONE
                        CoroutineScope(Dispatchers.IO).launch {
                            categoryDatabase.notificationDao().insert(
                                notification = Notification(
                                    id = id,
                                    dateLimit = args[2],
                                    title = args[0],
                                    desc = args[1]
                                )
                            )
                        }
                        dismiss()
                    }

                    NotificationsDialogUiState.Default -> {
                        binding.descNotificationDialogError.visibility = View.GONE
                        binding.titleNotificationDialogError.visibility = View.GONE
                        binding.dateNotificationDialogError.visibility = View.GONE


                    }
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            saveButtonNotificationDialog.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    id = dataStore.getValueFromKey("email")!!
                }
                args = listOf(binding.textInputTitleNotificationDialog.editText!!.text.toString(),
                    binding.textInputDescNotificationDialog.editText!!.text.toString(),
                    binding.textInputDateNotificationDialog.editText!!.text.toString())

                vm.setArgs(args)

                setObservables()

            }
            cancelButtonNotificationDialog.setOnClickListener{
                dismiss()
            }
        }
    }

}