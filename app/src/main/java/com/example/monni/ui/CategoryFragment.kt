package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.entity.Register
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.databinding.FragmentCategoryBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CategoryFragment : Fragment(R.layout.fragment_category), RegistersAdapter.RegisterItemListener {
    private val args: CategoryFragmentArgs by navArgs()

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentCategoryBinding
    private var registersList: MutableList<Register> = mutableListOf()
    private lateinit var txtTitle: TextView
    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var limitVM: LimitDialogViewModel

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
        recyclerView = binding.fragmentCategoryRecycler
        categoryDatabase = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()
        limitVM = LimitDialogViewModel()
        setInfo()
        setupRecyclers()
        setListeners()
    }

    private fun setInfo(){
        txtTitle = binding.fragmentCategoryTxtTitle
        txtTitle.text = args.categoryName
        CoroutineScope(Dispatchers.IO).launch {
            val registers = categoryDatabase.registerDao().getRegisters(args.email, args.categoryName)
            if (registers.isNotEmpty()) {
                registersList.addAll(registers)
            } else {
                registersList = mutableListOf()
            }
            CoroutineScope(Dispatchers.Main).launch {
                setupRecyclers()
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            imageViewCategoryFragmentMoreOptions.setOnClickListener {
                CategoryDialogFragment(args.categoryName, args.email).show(parentFragmentManager,"dialog")
            }
            fragmentCategoryIcReturn.setOnClickListener {
                requireView().findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToHomeFragment(args.email))
            }
        }
    }

    private fun setupRecyclers(){
        lifecycleScope.launch(Dispatchers.Main) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.setHasFixedSize(false)
            recyclerView.adapter = RegistersAdapter(registersList, this@CategoryFragment)
        }
    }

    override fun onRegisterItemClicked(register: Register, position: Int) {
        NewRegisterDialogFragment(args.categoryName, register.registerId, args.email).show(parentFragmentManager, "dialog")
    }
}