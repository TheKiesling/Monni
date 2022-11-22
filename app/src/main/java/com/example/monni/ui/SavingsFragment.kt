package com.example.monni.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.entity.SavingTip
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.data.local.source.InitializerSavingTips
import com.example.monni.databinding.FragmentSavingsBinding
import kotlinx.coroutines.*


class SavingsFragment : Fragment(R.layout.fragment_savings), SavingsAdapter.SavingsTipsItemListener{
    private val args: SavingsFragmentArgs by navArgs()
    private lateinit var binding: FragmentSavingsBinding
    private lateinit var recyclerView: RecyclerView
    private var userGoal = 0.0
    private var userSavings = 0.0
    private var listOfTips: MutableList<SavingTip> = mutableListOf()
    private lateinit var categoryDatabase: CategoryDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryDatabase = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()

        recyclerView = binding.savingsRecyclerView

        val progressBar: ProgressBar = binding.progressBarSavingsFragment
        val progressBarGoalText = binding.textViewSavingsFragmentSavingsGoal
        val progressBarSavingsText = binding.textViewSavingsFragmentSavings
        val savingsText = binding.textViewSavingsFragmentGoalText

        CoroutineScope(Dispatchers.IO).launch {
            userSavings = categoryDatabase.userDao().getUser(args.email).savings
            userGoal = categoryDatabase.userDao().getUser(args.email).goal
            CoroutineScope(Dispatchers.Main).launch {
                savingsText.text =
                    ((userSavings / userGoal) * 100).toInt().toString() + "% alcanzado"
                progressBar.progress = ((userSavings / userGoal) * 100).toInt()
                progressBarGoalText.text = "Q." + userGoal.toString()
                progressBarSavingsText.text = "Q." + userSavings.toString()
            }
        }

        setInfo()
        setListeners()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListeners() {
        binding.apply{
            textViewSavingsFragmentMore.setOnClickListener {
                GoalDialogFragment().show(parentFragmentManager,"dialog")
            }
            buttonSavingsFragmentSupport.setOnClickListener{
                TipDialogFragment().show(parentFragmentManager,"dialog")
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecyclers(){

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = SavingsAdapter(listOfTips, this)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onTipItemClicked(tip: SavingTip, position: Int) {
    }

    private fun setInfo(){
        CoroutineScope(Dispatchers.IO).launch {
            var tips = categoryDatabase.savingTipsDao().getSavingTips()
            if(tips.isEmpty()){
                InitializerSavingTips.createTipCall(categoryDatabase)
                tips = categoryDatabase.savingTipsDao().getSavingTips()
            }
            listOfTips = tips
            CoroutineScope(Dispatchers.Main).launch {
                setupRecyclers()
            }
        }
    }
}