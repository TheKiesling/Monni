package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
<<<<<<< Updated upstream
=======
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
>>>>>>> Stashed changes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monni.R
<<<<<<< Updated upstream
import com.example.monni.database.Database
import com.example.monni.database.Notification
=======
import com.example.monni.data.local.entity.Notification
import com.example.monni.data.local.source.CategoryDatabase
>>>>>>> Stashed changes
import com.example.monni.databinding.FragmentNotificationsBinding

<<<<<<< Updated upstream
class NotificationsFragment : Fragment(R.layout.fragment_notifications), NotificationsAdapter.NotificationItemListener {
=======
class NotificationsFragment() : Fragment(R.layout.fragment_notifications), NotificationsAdapter.NotificationItemListener {
    private val args: NotificationsFragmentArgs by navArgs()
>>>>>>> Stashed changes
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationsAdapter
    private lateinit var binding: FragmentNotificationsBinding
<<<<<<< Updated upstream
    private lateinit var notiList: List<Notification>

=======
    private  var notiList: MutableList<Notification> = mutableListOf()
    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var callback: ItemTouchHelper.SimpleCallback
    private lateinit var helper: ItemTouchHelper
>>>>>>> Stashed changes

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        recyclerView = binding.notificationRv

<<<<<<< Updated upstream
        setupRecyclers()
=======
        callback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                notiList.removeAt(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                CoroutineScope(Dispatchers.IO).launch {
                    var temporalList =
                        categoryDatabase.notificationDao().getNotifications(args.email)
                    var notification = temporalList.minus(notiList)
                    categoryDatabase.notificationDao().delete(notification[0].notificationID)
                }
                Toast.makeText(requireContext(), "Item eliminado", Toast.LENGTH_LONG).show()
            }
        }

        helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(recyclerView)
        setInfo()
>>>>>>> Stashed changes
        setListeners()
    }

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

    private fun setupRecyclers(){
        notiList = Database.getNotis()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = NotificationsAdapter(notiList, this)

    }
<<<<<<< Updated upstream
=======

    override fun onNotificationItemClicked(notification: Notification, position: Int) {
        TODO("Not yet implemented")
    }


>>>>>>> Stashed changes
}