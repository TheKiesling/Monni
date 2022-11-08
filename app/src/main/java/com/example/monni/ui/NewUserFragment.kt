package com.example.monni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.monni.R
import com.example.monni.databinding.FragmentNewUserBinding


class NewUserFragment : Fragment(R.layout.fragment_new_user) {
    private lateinit var binding: FragmentNewUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            buttonLoginFragmentLogin.setOnClickListener{
                requireView().findNavController().navigate(R.id.action_newUserFragment_to_homeFragment)
            }
        }
    }
}