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
import com.example.monni.databinding.FragmentLoginBinding

class RegistersAdapter(
    private val dataSet: MutableList<Register>,
    private val registerItemListener: RegisterItemListener
): RecyclerView.Adapter<RegistersAdapter.ViewHolder>() {

    interface RegisterItemListener {
        fun onRegisterItemClicked(register: Register, position: Int)
    }

    class ViewHolder(
        private val view: View,
        private val listener: RegisterItemListener
    ): RecyclerView.ViewHolder(view){

        private lateinit var binding: CategoryItemBinding

        private val description: TextView = binding.textViewCategoryItemDesc
        private val date: TextView = binding.textViewCategoryItemDate
        private val amount: TextView = binding.textViewCategoryItemAmount
        private val layout: LinearLayout = binding.layoutItemCategory
        private lateinit var register: Register

        fun setData(register: Register){
            this.register = register

            date.text = register.date.toString()
            description.text = register.description
            amount.text = register.amount.toString()

            layout.setOnClickListener{
                listener.onRegisterItemClicked(register, this.adapterPosition)
            }
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