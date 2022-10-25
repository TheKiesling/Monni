package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monni.R
import com.example.monni.database.Category
import com.example.monni.database.Database
import com.example.monni.database.Register
import com.example.monni.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment(R.layout.fragment_category), RegistersAdapter.RegisterItemListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var registersList: MutableList<Register>

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
        registersList = Database.getRegisters()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = RegistersAdapter(registersList, this)
    }

    override fun onRegisterItemClicked(register: Register, position: Int) {

    }


}