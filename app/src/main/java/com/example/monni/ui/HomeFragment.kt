package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monni.R
import com.example.monni.data.local.entity.Category
import com.example.monni.data.remote.firestore.FirestoreCategoryApiImpl
import com.example.monni.data.repository.categories.CategoryRepository
import com.example.monni.data.repository.categories.CategoryRepositoryImpl
import com.example.monni.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), CategoriesAdapter.CategoryItemListener {
    @Inject
    lateinit var firestore: FirebaseFirestore
    private val args: HomeFragmentArgs by navArgs()
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoriesList: List<Category>

    @Inject
    lateinit var repository: CategoryRepository

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
        repository = CategoryRepositoryImpl(
            FirestoreCategoryApiImpl(Firebase.firestore)
        )
        prueba()
        setupRecyclers()
        setListeners()
    }

    private fun prueba(){

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
        lifecycleScope.launch(Dispatchers.IO) {
            categoriesList = repository.getCategories(args.id)
            lifecycleScope.launch(Dispatchers.Main) {
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter = CategoriesAdapter(categoriesList, this@HomeFragment)
            }
        }
    }

    override fun onCategoryItemClicked(category: Category, position: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(category.name)
        requireView().findNavController().navigate(action)
    }


}