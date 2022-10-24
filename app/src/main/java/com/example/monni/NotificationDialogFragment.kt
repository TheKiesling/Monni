package com.example.monni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.monni.databinding.FragmentNewRegisterDialogBinding
import com.example.monni.databinding.FragmentNotificationDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotificationDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            saveButtonLimitDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_notificationDialogFragment2_to_notificationsFragment)
            }
            cancelButtonLimitDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_notificationDialogFragment2_to_notificationsFragment)
            }
        }
    }
}