package com.example.monni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.monni.databinding.FragmentLimitDialogBinding
import com.example.monni.databinding.FragmentNewRegisterDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NewRegisterDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewRegisterDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewRegisterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            saveButtonNewRegisterDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_newRegisterDialogFragment_to_categoryFragment)
            }
            cancelButtonNewRegisterDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_newRegisterDialogFragment_to_categoryFragment)
            }
        }
    }
}