package com.example.monni.ui

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
import com.example.monni.data.local.entity.Category
import com.example.monni.data.local.source.CategoryDatabase
import com.example.monni.data.local.storage.DataStorage
import com.example.monni.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), CategoriesAdapter.CategoryItemListener {
    private val args: HomeFragmentArgs by navArgs()
    private lateinit var dataStore: DataStorage
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentHomeBinding
    private var categoriesList: MutableList<Category> = mutableListOf()
    private lateinit var categoryDatabase: CategoryDatabase
    private lateinit var name: String

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

        recyclerView = binding.recyclerViewHomeFragment
        dataStore = DataStorage(requireContext())
        categoryDatabase = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java,
            "dbname"
        ).build()
        setInfo()
        setListeners()
    }

    private fun setInfo(){
        CoroutineScope(Dispatchers.IO).launch {
            val categories = categoryDatabase.categoryDao().getCategories(args.email)
            categoriesList.addAll(categories)
            CoroutineScope(Dispatchers.Main).launch {
                setupRecyclers()
            }

            if (dataStore.getValueFromKey("name") != null) {
                name = dataStore.getValueFromKey("name")!!
                CoroutineScope(Dispatchers.Main).launch {
                    binding.apply {
                        binding.textviewHomeFragmentUsername.text = name
                    }
                }
            }
        }
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
                requireView().findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNotificationsFragment(args.email))
            }

            exitIconHomeFragment.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    dataStore.removeKey("email")
                    CoroutineScope(Dispatchers.Main).launch {
                        requireView().findNavController().navigate(R.id.action_toLoginFragment)
                    }
                }
            }
        }
    }

    private fun setupRecyclers() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = CategoriesAdapter(categoriesList, this@HomeFragment)
    }

    override fun onCategoryItemClicked(category: Category, position: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(category.name, args.email)
        requireView().findNavController().navigate(action)
    }
}