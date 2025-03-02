package com.example.headhunterapp.presentation.massagefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.headhunterapp.R
import com.example.headhunterapp.databinding.FragmentMassageBinding


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
        tabButton()
    }

    fun tabButton() {
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