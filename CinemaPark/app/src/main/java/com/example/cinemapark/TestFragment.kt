package com.example.cinemapark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.databinding.FragmentHomepageBinding
import com.example.cinemapark.databinding.FragmentLoaderBinding
import com.example.cinemapark.databinding.FragmentTestBinding
import com.example.cinemapark.presentation.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    var idKino = 0
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
/*            idKino = viewModel.state.value*/
            runBlocking {

                viewModel.reloadTest(111111)
                delay(1000)


                binding.textView2.text = "${viewModel.state.value}"
            }
            binding.button.setOnClickListener {

            }
        }
    }
}