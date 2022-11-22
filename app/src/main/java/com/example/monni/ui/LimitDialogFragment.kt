package com.example.monni.ui

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.entity.Category
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.data.local.storage.DataStorage
import com.example.monni.databinding.FragmentLimitDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

@Module
@AndroidEntryPoint
@InstallIn(FragmentComponent::class)
class LimitDialogFragment(
    private val category: String,
    private val id: String
) : DialogFragment() {
    private lateinit var dataStore: DataStorage
    private lateinit var binding: FragmentLimitDialogBinding
    private lateinit var categoryDB: CategoryDatabase
    private var currentLimit: Double = 0.0
    private var amount: Double = 0.0
    private lateinit var vm: LimitDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLimitDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentLimitDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        vm = LimitDialogViewModel()
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dataStore = DataStorage(requireContext())
        categoryDB = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()

        CoroutineScope(Dispatchers.IO).launch {
            currentLimit = categoryDB.categoryDao().getCategory(id, category).limit
            amount = categoryDB.categoryDao().getCategory(id, category).amount
            binding.currentLimitTextDialog.text = "Límite actual: Q${currentLimit}"
        }

        setListeners()

        return dialog
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            vm.uiState.collectLatest { state ->
                vm.getNewLimit()
                when(state){
                    is GeneralUiState.Error -> {
                        Toast.makeText(requireContext(), state.msg, Toast.LENGTH_LONG).show()
                    }
                    GeneralUiState.Success -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            categoryDB.categoryDao().updateLimit(id, category, currentLimit)
                        }
                        dismiss()
                    }
                    GeneralUiState.Default -> {
                    }
                }
            }
        }
    }

    private fun setListeners() {

        binding.apply{
            saveButtonLimitDialog.setOnClickListener {

                try{
                    currentLimit = textInputModifyLimitDialog.editText!!.text.toString().toDouble()
                } catch(e: NumberFormatException){
                    currentLimit = 0.0
                }

                vm.setPrevLimit(currentLimit, amount)

                setObservables()
            }
            cancelButtonLimitDialog.setOnClickListener {
                dismiss()
            }
        }
    }
}