package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.monni.R
import com.example.monni.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {
    private lateinit var binding: FragmentNotificationsBinding
    //private lateinit var notificationsList: MutableList<Notification>

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

        setupRecyclers()
        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            notificationBtnAdd.setOnClickListener {
                NotificationDialogFragment().show(parentFragmentManager,"dialog")
            }
        }
    }

    private fun setupRecyclers(){
        //notificationsList = MonniDB.getCharacters()
        //recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //recyclerView.setHasFixedSize(true)

    }
}