package com.example.monni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.monni.databinding.FragmentCategoryDialogBinding
import com.example.monni.databinding.FragmentGoalDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class goalDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentGoalDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            saveButtonGoalDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_goalDialogFragment2_to_savingsFragment)
            }
            cancelButtonGoalDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_goalDialogFragment2_to_savingsFragment)
            }
        }
    }

}