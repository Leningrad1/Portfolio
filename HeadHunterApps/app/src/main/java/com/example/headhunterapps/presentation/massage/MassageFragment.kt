package com.example.headhunterapps.presentation.massage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.headhunterapps.R
import com.example.headhunterapps.databinding.FragmentMassageBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MassageFragment : Fragment() {
    private lateinit var binding: FragmentMassageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMassageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            tabButton()
        }
    }

    private fun tabButton() {
        lifecycleScope.launch {
            binding.profile.setOnClickListener {
                findNavController().navigate(
                    R.id.action_massageFragment_to_entryEmailFragment
                )
            }
            binding.home.setOnClickListener {
                findNavController().navigate(
                    R.id.action_massageFragment_to_homeFragment
                )
            }
            binding.favourites.setOnClickListener {
                findNavController().navigate(
                    R.id.action_massageFragment_to_favoritesFragment
                )
            }
            binding.send.setOnClickListener {
                findNavController().navigate(
                    R.id.action_massageFragment_to_responseFragment
                )
            }
        }
    }
}