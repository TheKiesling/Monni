package com.example.monni.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.data.local.storage.DataStorage
import com.example.monni.databinding.FragmentLimitDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LimitDialogFragment(
    category: String
) : BottomSheetDialogFragment() {
    private lateinit var dataStore: DataStorage
    private lateinit var binding: FragmentLimitDialogBinding
    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var id: String

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
        dataStore = DataStorage(requireContext())
        categoryDatabase = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()
        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            saveButtonLimitDialog.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {
                    id = dataStore.getValueFromKey("email")!!
                }

                val limit = textInputModifyLimitDialog.editText!!.text.toString().toDouble()

                CoroutineScope(Dispatchers.IO).launch{
                    categoryDatabase.categoryDao().updateLimit(id, category, limit)
                }

                requireView().findNavController().navigate(R.id.action_limitDialogFragment_to_categoryFragment)
            }
            cancelButtonLimitDialog.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_limitDialogFragment_to_categoryFragment)
            }
        }
    }
}