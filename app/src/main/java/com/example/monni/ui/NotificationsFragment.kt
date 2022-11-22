package com.example.monni.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.entity.Notification
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.databinding.FragmentNotificationsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class NotificationsFragment : Fragment(R.layout.fragment_notifications), NotificationsAdapter.NotificationItemListener {
    private val args: NotificationsFragmentArgs by navArgs()
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var notiList: MutableList<Notification>
    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var callback: ItemTouchHelper.SimpleCallback
    private lateinit var helper: ItemTouchHelper
    private lateinit var adapter: NotificationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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
            var notifications = categoryDatabase.notificationDao().getNotifications(args.email)
            notiList = notifications
            CoroutineScope(Dispatchers.Main).launch {

                setupRecyclers()
                callback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder,
                    ): Boolean {
                        return false
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        notiList.removeAt(viewHolder.adapterPosition)
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)

                        CoroutineScope(Dispatchers.IO).launch {
                            val temporalList =
                                categoryDatabase.notificationDao().getNotifications(args.email)
                            val notification = temporalList.minus(notiList.toSet())
                            categoryDatabase.notificationDao().delete(notification[0].title)
                        }
                        Toast.makeText(requireContext(), "Item eliminado", Toast.LENGTH_LONG).show()
                        setupRecyclers()
                    }
                }

                helper = ItemTouchHelper(callback)
                helper.attachToRecyclerView(recyclerView)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListeners() {
        binding.apply{
            notificationBtnAdd.setOnClickListener {
                NotificationDialogFragment().show(parentFragmentManager,"dialog")
                CoroutineScope(Dispatchers.IO).launch {
                    var notifications = categoryDatabase.notificationDao().getNotifications(args.email)
                    notiList = notifications
                }
                setupRecyclers()
            }

            notificationsBtnReturn.setOnClickListener {
                requireView().findNavController().navigate(NotificationsFragmentDirections.actionNotificationsFragmentToHomeFragment(args.email))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecyclers(){
        adapter = NotificationsAdapter(notiList.sortedBy { LocalDate.of(it.dateLimit.substring(6).toInt(),
            it.dateLimit.substring(3,5).toInt(), it.dateLimit.substring(0,2).toInt()) }, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

    }

}