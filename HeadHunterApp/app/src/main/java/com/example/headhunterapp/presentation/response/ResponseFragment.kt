package com.example.headhunterapp.presentation.response

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.headhunterapp.R
import com.example.headhunterapp.databinding.FragmentResponseBinding
import com.example.headhunterapp.presentation.ViewModel
import kotlinx.coroutines.launch


class ResponseFragment : Fragment() {

    private lateinit var binding: FragmentResponseBinding
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResponseBinding.inflate(inflater, container, false)
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
                    R.id.action_responseFragment_to_entryEmailFragment
                )
            }
            binding.home.setOnClickListener {
                findNavController().navigate(
                    R.id.action_responseFragment_to_homeFragment
                )
            }
            binding.favourites.setOnClickListener {
                findNavController().navigate(
                    R.id.action_responseFragment_to_favoritesFragment
                )
            }
            binding.massage.setOnClickListener {
                findNavController().navigate(
                    R.id.action_responseFragment_to_massageFragment
                )
            }
        }
    }

}