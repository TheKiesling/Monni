package com.example.monni.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.databinding.FragmentGoalDialogBinding
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

@Module
@AndroidEntryPoint
@InstallIn(FragmentComponent::class)
class GoalDialogFragment(private val email:String): DialogFragment() {
    private lateinit var binding : FragmentGoalDialogBinding
    private var currentGoal: Double = 0.0
    private var currentSavings: Double = 0.0
    private lateinit var categoryDB: CategoryDatabase
    private lateinit var vm: GoalDialogViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentGoalDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        vm = GoalDialogViewModel()
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        categoryDB = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()

        CoroutineScope(Dispatchers.IO).launch {
            currentGoal = categoryDB.userDao().getUser(email).goal
            currentSavings = categoryDB.userDao().getUser(email).savings
            binding.currentGoalTextDialog.text = "Límite actual: Q${currentGoal}"
        }

        setListeners()
        return dialog
    }

    private fun setListeners() {

        binding.apply {
            saveButtonGoalDialog.setOnClickListener{
                try{
                    currentGoal = textInputModifyGoalDialog.editText!!.text.toString().toDouble()
                }
                catch(e: NumberFormatException){
                    currentGoal = 300.0
                }
                vm.setPrevLimit(currentGoal, currentSavings)
                setObservables()
            }
            cancelButtonGoalDialog.setOnClickListener{
                dismiss()
            }
        }
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            vm.uiState.collectLatest { state ->
                vm.getNewGoal()
                when(state){
                    is GeneralUiState.Error -> {
                        Toast.makeText(requireContext(), state.msg, Toast.LENGTH_LONG).show()
                    }
                    GeneralUiState.Success -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            categoryDB.userDao().updateLimit(email,currentGoal)
                        }
                        dismiss()
                    }
                    GeneralUiState.Default -> {
                    }
                }
            }
        }
    }
}