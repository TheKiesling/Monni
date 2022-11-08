package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monni.R
import com.example.monni.database.Database
import com.example.monni.database.Notification
import com.example.monni.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment(R.layout.fragment_notifications), NotificationsAdapter.NotificationItemListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var notiList: List<Notification>


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

        setupRecyclers()
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
}