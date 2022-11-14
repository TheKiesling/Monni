package com.example.monni.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.monni.R
import com.example.monni.data.local.entity.Category

class CategoriesAdapter(
    private val dataSet: List<Category>,
    private val categoryItemListener: CategoryItemListener

): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    interface CategoryItemListener {
        fun onCategoryItemClicked(category: Category, position: Int)
    }

    class ViewHolder(
        private val view: View,
        private val listener: CategoryItemListener
    ): RecyclerView.ViewHolder(view){

        private val txtName: TextView = view.findViewById(R.id.textView_progressBarItem_name)
        private val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        private val layout: ConstraintLayout = view.findViewById(R.id.layout_itemProgressBar)
        private lateinit var category: Category

        fun setData(category: Category){
            this.category = category

            txtName.text = category.name
            progressBar.progress = (100*category.amount/category.limit).toInt()
            progressBar.progressTintList = ColorStateList.valueOf(Color.parseColor(category.color))

            layout.setOnClickListener{
                listener.onCategoryItemClicked(category, this.adapterPosition)
            }
        }
    }

    override fun getItemCount() = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.progress_bar_item, parent, false)

        return ViewHolder(view, categoryItemListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }

}