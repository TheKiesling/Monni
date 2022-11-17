package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monni.R
import com.example.monni.data.local.entity.Register
import com.example.monni.data.remote.firestore.FirestoreRegisterApiImpl
import com.example.monni.data.repository.register.RegisterRepository
import com.example.monni.data.repository.register.RegisterRepositoryImpl
import com.example.monni.databinding.FragmentCategoryBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CategoryFragment : Fragment(R.layout.fragment_category), RegistersAdapter.RegisterItemListener {
    private val args: CategoryFragmentArgs by navArgs()

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var registersList: MutableList<Register>
    private lateinit var txtTitle: TextView
    private lateinit var repository: RegisterRepository

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

        txtTitle = binding.fragmentCategoryTxtTitle
        recyclerView = binding.fragmentCategoryRecycler
        txtTitle.text = args.categoryName
        repository = RegisterRepositoryImpl(
            FirestoreRegisterApiImpl(Firebase.firestore)
        )
        setupRecyclers()
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            imageViewCategoryFragmentMoreOptions.setOnClickListener {
                CategoryDialogFragment().show(parentFragmentManager,"dialog")
            }
        }
    }

    private fun setupRecyclers(){
        lifecycleScope.launch(Dispatchers.IO) {
            registersList = repository.getRegisters(args.categoryName)?.toMutableList()!!
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = RegistersAdapter(registersList, this@CategoryFragment)
        }
    }
}