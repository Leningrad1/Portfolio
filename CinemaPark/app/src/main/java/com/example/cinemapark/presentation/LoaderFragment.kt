package com.example.cinemapark.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.cinemapark.R
import com.example.cinemapark.databinding.FragmentLoaderBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoaderFragment : Fragment() {

    private var _binding: FragmentLoaderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(1000)

            Navigation.findNavController( requireActivity() , R.id.nav_host).navigate(R.id.homepageFragment)
        }
        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_loaderFragment_to_homepageFragment)
        }
    }
}