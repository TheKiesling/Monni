package com.example.monni.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.monni.R
import com.example.monni.databinding.CategoryItemBinding
import com.example.monni.database.Register

class RegistersAdapter(
    private val dataSet: MutableList<Register>,
    private val registerItemListener: RegisterItemListener
): RecyclerView.Adapter<RegistersAdapter.ViewHolder>() {

    interface RegisterItemListener {

    }

    class ViewHolder(
        private val view: View,
        private val listener: RegisterItemListener
    ): RecyclerView.ViewHolder(view){

        //private lateinit var binding: CategoryItemBinding

        private val description: TextView = view.findViewById(R.id.textView_categoryItem_desc)
        private val date: TextView = view.findViewById(R.id.textView_categoryItem_date)
        private val amount: TextView = view.findViewById(R.id.textView_categoryItem_amount)
        private val layout: LinearLayout = view.findViewById(R.id.layout_itemCategory)
        private lateinit var register: Register

        fun setData(register: Register){
            this.register = register

            date.text = register.date.toString()
            description.text = register.description
            amount.text = register.amount.toString()

        }

    }

    override fun getItemCount() = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)

        return ViewHolder(view, registerItemListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }

}