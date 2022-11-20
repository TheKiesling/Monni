package com.example.monni.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.entity.Notification
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.data.local.storage.DataStorage
import com.example.monni.database.Database
import com.example.monni.databinding.FragmentNotificationsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class NotificationsFragment : Fragment(R.layout.fragment_notifications), NotificationsAdapter.NotificationItemListener {
    private val args: NotificationsFragmentArgs by navArgs()
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var notiList: List<Notification>
    private lateinit var categoryDatabase: CategoryDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
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
        recyclerView = binding.notificationRv

        setInfo()
        setListeners()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setInfo(){
        CoroutineScope(Dispatchers.IO).launch {
            val notifications = categoryDatabase.notificationDao().getNotifications(args.email)
            notiList = notifications
            CoroutineScope(Dispatchers.Main).launch {
                setupRecyclers()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListeners() {
        binding.apply{
            notificationBtnAdd.setOnClickListener {
                NotificationDialogFragment().show(parentFragmentManager,"dialog")
            }

            notificationsBtnReturn.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_notificationsFragment_to_homeFragment)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecyclers(){
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = NotificationsAdapter(notiList.sortedBy { LocalDate.of(it.dateLimit.substring(6).toInt(),
            it.dateLimit.substring(3,5).toInt(), it.dateLimit.substring(0,2).toInt()) }, this)

    }

}