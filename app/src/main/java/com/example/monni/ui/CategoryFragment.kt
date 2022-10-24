package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.monni.R
import com.example.monni.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment(R.layout.fragment_category) {
    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclers()
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            imageViewCategoryFragmentMoreOptions.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_categoryFragment_to_categoryDialogFragment2)
            }
        }
    }

    private fun setupRecyclers(){
        //categoriesList = MonniDB.getCharacters()
        //recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //recyclerView.setHasFixedSize(true)

    }


}