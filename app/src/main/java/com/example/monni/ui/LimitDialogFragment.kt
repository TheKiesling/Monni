package com.example.monni.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.monni.R
import com.example.monni.databinding.FragmentLimitDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class LimitDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentLimitDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLimitDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            saveButtonLimitDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_limitDialogFragment_to_categoryFragment)
            }
            cancelButtonLimitDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_limitDialogFragment_to_categoryFragment)
            }
        }
    }
}