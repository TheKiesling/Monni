package com.example.monni.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monni.R
import com.example.monni.data.local.entity.SavingTip
import com.example.monni.database.Database
import com.example.monni.database.User
import com.example.monni.databinding.FragmentSavingsBinding
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SavingsFragment : Fragment(R.layout.fragment_savings), SavingsAdapter.SavingsTipsItemListener{
    private lateinit var binding: FragmentSavingsBinding
    private lateinit var recyclerView: RecyclerView
    private var database: Database = Database
    lateinit var listOfTips: List<SavingTip>

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
        val progressBar: ProgressBar = binding.progressBarSavingsFragment
        val progressBarGoalText = binding.textViewSavingsFragmentSavingsGoal
        val progressBarSavingsText = binding.textViewSavingsFragmentSavings
        val savingsText = binding.textViewSavingsFragmentGoalText
        val user: User = database.getUser("the_kiesling")
        val userSavings = user.savings
        val userGoal = user.goal


        savingsText.text = "Has alcanzado el ${((userSavings/userGoal)* 100).toInt()}% de tu meta"
        progressBar.progress = ((userSavings/userGoal)* 100).toInt()
        progressBarGoalText.text = "Q." + userGoal.toString()
        progressBarSavingsText.text = "Q." + userSavings.toString()
        recyclerView = binding.savingsRecyclerView

        if(userSavings==userGoal){
            progressBarGoalText.visibility = View.GONE
        }
        setupRecyclers()
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
        val database = Database
        listOfTips = database.getSavingTips()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = SavingsAdapter(listOfTips, this)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onTipItemClicked(tip: SavingTip, position: Int) {
    }
}