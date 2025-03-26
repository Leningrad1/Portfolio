package com.example.headhunterapps.presentation.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.headhunterapp.model.Vacancy
import com.example.headhunterapps.R
import com.example.headhunterapps.presentation.ViewModel
import com.example.headhunterapps.databinding.FragmentFavoritesBinding
import com.example.headhunterapps.presentation.recyclerviewadapter.VacancyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: ViewModel by viewModels()
    private var favouriteVacancy = mutableListOf<Vacancy>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            lifecycleScope.launch {
                tabButton()
                favouriteList()
            }
        } catch (e: Exception) {
        }
    }

    private fun favouriteList() {
        lifecycleScope.launch {
            try {
                viewModel.vacanciesList.forEach {
                    if (it.isFavorite) {
                        favouriteVacancy.clear()
                        favouriteVacancy.add(it)
                    }
                    binding.vacancyCount.text =
                        viewModel.getVacancyPluralForm(viewModel.allLikeVacancy.value.size)
                }
                val adapter = VacancyAdapter(
                    requireContext(),
                    favouriteVacancy,
                    ::onItemClick,
                    viewModel
                )
                val recyclerViewVacancy = binding.recyclerFavourite
                recyclerViewVacancy.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                recyclerViewVacancy.adapter = adapter
                viewModel.allLikeVacancy.collect { likeVacancies ->
                    viewModel.vacanciesList.forEach { vacancy ->
                        if (likeVacancies.any { it.id == vacancy.id && it.isFavorite }) {
                            if (!favouriteVacancy.contains(vacancy)) {
                                vacancy.isFavorite = true
                                favouriteVacancy.add(vacancy)
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                    binding.vacancyCount.text =
                        viewModel.getVacancyPluralForm(favouriteVacancy.size)
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun onItemClick() {
        TODO("ЗАГЛУШКА")
    }

    private fun tabButton() {
        lifecycleScope.launch {
            binding.profile.setOnClickListener {
                findNavController().navigate(
                    R.id.action_favoritesFragment_to_entryEmailFragment
                )
            }
            binding.home.setOnClickListener {
                findNavController().navigate(
                    R.id.action_favoritesFragment_to_homeFragment
                )
            }
            binding.send.setOnClickListener {
                findNavController().navigate(
                    R.id.action_favoritesFragment_to_responseFragment
                )
            }
            binding.massage.setOnClickListener {
                findNavController().navigate(
                    R.id.action_favoritesFragment_to_massageFragment
                )
            }
        }
    }
}