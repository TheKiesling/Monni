package com.example.monni.ui
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monni.R
import com.example.monni.database.Category
import com.example.monni.database.Database
import com.example.monni.databinding.FragmentPieChartBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class PieChartFragment : Fragment(R.layout.fragment_pie_chart) {
    private lateinit var recyclerView: RecyclerView
    private val listOfEntries: MutableList<PieEntry> = arrayListOf()
    private val listOfColors: MutableList<Int> = arrayListOf()
    private lateinit var pieChart: PieChart
    private lateinit var binding: FragmentPieChartBinding
    private var database: Database = Database
    private lateinit var categoriesList: List<com.example.monni.database.Category>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPieChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pieChart = binding.expensesPieChart
        recyclerView = binding.recyclerViewExpensesPieFragment
        setupRecyclers()
        setUpPieChart("the_kiesling")
    }

    private fun setUpPieChart(username: String){
        for(category in database.getCategories(username)) {
            listOfColors.add(ColorTemplate.rgb(category.color))
            val totalOfCategories: Double = database.getTotalCategories(username)
            listOfEntries.add(
                PieEntry(
                    (category.amount / totalOfCategories).toFloat(),
                    category.name
                )
            )
        }
        declarePieChartData(listOfEntries,listOfColors,pieChart)
    }
    private fun setupRecyclers(){
            categoriesList = database.getCategories("the_kiesling")
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = PieChartAdapter(categoriesList)
            }

    private fun declarePieChartData(data: List<PieEntry>, colors: List<Int>, pieChart: PieChart){
        val pieDataSet: PieDataSet = PieDataSet(data,"Expense")
        pieDataSet.setColors(colors)
        val pieData: PieData = PieData(pieDataSet)
        pieChart.setData(pieData)
    }
}

