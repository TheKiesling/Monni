package com.example.monni.ui
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.entity.Category
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.data.local.source.InitializerSavingTips
import com.example.monni.database.Database
import com.example.monni.databinding.FragmentPieChartBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PieChartFragment : Fragment(R.layout.fragment_pie_chart) {
    private val args: PieChartFragmentArgs by navArgs()
    private lateinit var recyclerView: RecyclerView
    private val listOfEntries: MutableList<PieEntry> = arrayListOf()
    private val listOfColors: MutableList<Int> = arrayListOf()
    private lateinit var pieChart: PieChart
    private lateinit var binding: FragmentPieChartBinding
    private lateinit var database: CategoryDatabase
    private var categoriesList: List<Category> = listOf()

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

        database = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()

        pieChart = binding.expensesPieChart
        recyclerView = binding.recyclerViewExpensesPieFragment
        setInfo()
        setUpPieChart(args.email)
    }

    private fun setUpPieChart(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            for (category in database.categoryDao().getCategories(args.email)) {
                listOfColors.add(ColorTemplate.rgb(category.color))
            }
            var totalOfCategories = 0.00
            for (category in database.categoryDao().getCategories(args.email)) {
                totalOfCategories += category.amount
                if (totalOfCategories.toInt() != 0) {
                    listOfEntries.add(
                        PieEntry(
                            (category.amount / totalOfCategories).toFloat(),
                            category.name
                        )
                    )
                    
                    CoroutineScope(Dispatchers.Main).launch {
                        declarePieChartData(listOfEntries, listOfColors, pieChart)
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(requireContext(), "Sin registros", Toast.LENGTH_LONG)
                            .show()
                    }
                    break
                }
            }
        }
    }


    private fun setupRecyclers(){
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = PieChartAdapter(categoriesList)
    }

    private fun declarePieChartData(data: List<PieEntry>, colors: List<Int>, pieChart: PieChart){
        val pieDataSet: PieDataSet = PieDataSet(data,"")
        pieDataSet.setColors(colors)
        val pieData: PieData = PieData(pieDataSet)
        pieData.setDrawValues(false)
        val legendPieChart: Legend = pieChart.getLegend()
        val description: Description = pieChart.getDescription()
        legendPieChart.setEnabled(false)
        pieChart.setDrawSliceText(false)
        description.setEnabled(false)
        pieChart.setData(pieData)
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.invalidate()
        pieChart.animateY(1400, Easing.EaseInOutQuad)

    }

    private fun setInfo(){
        CoroutineScope(Dispatchers.IO).launch {
            var categories = database.categoryDao().getCategories(args.email)
            categoriesList = categories
            CoroutineScope(Dispatchers.Main).launch {
                setupRecyclers()
            }
        }
    }
}

