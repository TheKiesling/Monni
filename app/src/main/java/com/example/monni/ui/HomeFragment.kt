package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.monni.R
import com.example.monni.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {
    //private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentHomeBinding
    //private lateinit var categoriesList: MutableList<Category>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclers()
        setListeners()
    }

    private fun setListeners() {
        binding.apply{
            bottomNavigationHomeFragment.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_item_savings -> requireView().findNavController().navigate(R.id.action_homeFragment_to_savingsFragment)

                    R.id.menu_item_graph -> requireView().findNavController().navigate(R.id.action_homeFragment_to_pieChartFragment)
                }
                true
            }
            imageViewHomeFragmentNotifications.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_homeFragment_to_notificationsFragment)
            }

            exitIconHomeFragment.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_toLoginFragment)
            }
        }
    }

    private fun setupRecyclers(){
        //categoriesList = MonniDB.getCharacters()
        //recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //recyclerView.setHasFixedSize(true)

    }





}