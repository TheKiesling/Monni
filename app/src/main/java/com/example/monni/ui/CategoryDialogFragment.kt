package com.example.monni.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.monni.R
import com.example.monni.databinding.FragmentCategoryDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCategoryDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            modifyLimitOptionDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_categoryDialogFragment2_to_limitDialogFragment)
            }
            newRegisterOptionDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_categoryDialogFragment2_to_newRegisterDialogFragment)
            }
        }
    }
}