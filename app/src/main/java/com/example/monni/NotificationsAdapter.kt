package com.example.monni.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.monni.R
import com.example.monni.database.Notification
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class NotificationsAdapter (
    private val dataSet: List<Notification>,
    private val notificationItemListener: NotificationItemListener,
    ): RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

        interface NotificationItemListener {

        }

        class ViewHolder(
            private val view: View,
            private val listener: NotificationItemListener
        ): RecyclerView.ViewHolder(view){

            //private lateinit var binding: CategoryItemBinding

            private val description: TextView = view.findViewById(R.id.notificationItem_desc)
            private val date: TextView = view.findViewById(R.id.txt_notificationItem_fechaLimite)
            private val title: TextView = view.findViewById(R.id.notificationItem_title)
            private val days: TextView = view.findViewById(R.id.txt_notificationItem_dayCounter)
            private lateinit var notification: Notification

            fun setData(noti: Notification){
                this.notification = noti
                date.text = notification.dateLimit.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                description.text = notification.desc
                title.text = notification.title
                days.text = ChronoUnit.DAYS.between(LocalDate.now(), noti.dateLimit).toString()
            }

        }

        override fun getItemCount() = dataSet.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.notification_item, parent, false)

            return ViewHolder(view, notificationItemListener)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setData(dataSet[position])
        }

    }