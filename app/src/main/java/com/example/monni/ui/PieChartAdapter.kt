package com.example.monni.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.monni.R
import java.math.RoundingMode
import java.text.DecimalFormat

class PieChartAdapter (
    private val dataSet: List<com.example.monni.database.Category>,
    ): RecyclerView.Adapter<PieChartAdapter.ViewHolder>() {

    class ViewHolder(
        private val view: View,
    ): RecyclerView.ViewHolder(view){
        private val colorOfCategory: ImageView = view.findViewById(R.id.icon_for_pie_chart_element)
        private val nameOfCategory: TextView = view.findViewById(R.id.category_text_pie_chart_element)
        private val totalAmountCategory: TextView = view.findViewById(R.id.money_amount_text_pie_chart_element)
        private lateinit var category: com.example.monni.database.Category

        fun setData(category: com.example.monni.database.Category) {
            this.category = category
            colorOfCategory.imageTintList = ColorStateList.valueOf(Color.parseColor(category.color))
            nameOfCategory.text = category.name
            val amount = DecimalFormat("#.##")
            amount.roundingMode = RoundingMode.CEILING
            totalAmountCategory.text = "Q." + (category.amount).toString().format("%. 2f", 2)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pie_chart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

}