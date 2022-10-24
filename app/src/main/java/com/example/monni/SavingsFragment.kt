package com.example.monni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.example.monni.databinding.FragmentGoalDialogBinding
import com.example.monni.databinding.FragmentLoginBinding
import com.example.monni.databinding.FragmentSavingsBinding


class SavingsFragment : Fragment(R.layout.fragment_savings) {
    private lateinit var binding: FragmentSavingsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclers()
        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            textViewSavingsFragmentMore.setOnClickListener {
                GoalDialogFragment().show(parentFragmentManager,"dialog")
            }
        }
    }

    private fun setupRecyclers(){
        //tipsList = MonniDB.getCharacters()
        //recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //recyclerView.setHasFixedSize(true)

    }
}