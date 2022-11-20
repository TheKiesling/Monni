package com.example.monni.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.monni.R
import com.example.monni.data.local.entity.SavingTip

class SavingsAdapter(
    private val dataSet: List<SavingTip>,
    private val savingsItemListener: SavingsTipsItemListener

): RecyclerView.Adapter<SavingsAdapter.ViewHolder>() {

    interface SavingsTipsItemListener {
        fun onTipItemClicked(tip: SavingTip, position: Int)
    }
    class ViewHolder(
        private val view: View,
        private val listener: SavingsTipsItemListener
    ): RecyclerView.ViewHolder(view){

        private val titleOfTip: TextView = view.findViewById(R.id.textView_savingsTipsItem)
        private val expandedView: RelativeLayout = view.findViewById(R.id.expanded_view)
        private val tipDescription: TextView = view.findViewById(R.id.savings_expanded_text)
        private val expandArrow: ImageView = view.findViewById(R.id.imageView_savingsTipsItem)
        private val layout: ConstraintLayout = view.findViewById(R.id.layout_itemSavingsTip)
        private lateinit var savingTip: SavingTip

        fun setData(savingTip: SavingTip){
            this.savingTip = savingTip
            titleOfTip.text = savingTip.name
            tipDescription.text = savingTip.description
            expandedView.visibility = View.GONE
            var angle =0f

                layout.setOnClickListener{
                if (!savingTip.expand){
                    if(angle.equals(0f)) {
                        angle += 90f
                        expandArrow.animate().rotationBy(90f).setDuration(200).start();
                    }
                    expandedView.visibility = View.VISIBLE
                }
                else{
                    if(angle.equals(90f)) {
                        angle -= 90f
                        expandArrow.animate().rotationBy(-90f).setDuration(200).start();
                    }
                    expandedView.visibility = View.GONE
                }
                savingTip.expand = !savingTip.expand
                listener.onTipItemClicked(savingTip, this.adapterPosition)
            }
        }
    }

    override fun getItemCount() = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.savings_tips_item, parent, false)

        return ViewHolder(view, savingsItemListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }
}