package com.example.monni.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.monni.R
import com.example.monni.databinding.FragmentTipDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TipDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTipDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTipDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            buttonLoginFragmentClose.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_tipDialogFragment_to_savingsFragment)
            }

        }
    }
}