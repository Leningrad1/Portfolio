package com.example.headhunterapp.presentation.favoritesfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.headhunterapp.R
import com.example.headhunterapp.databinding.FragmentFavoritesBinding
import com.example.headhunterapp.dataclass.Vacancy
import com.example.headhunterapp.presentation.ViewModel
import com.example.headhunterapp.presentation.homefragment.HomeFragment
import com.example.headhunterapp.presentation.recyclerview.VacancyAdapter
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: ViewModel by viewModels()
    private val homeFragment = HomeFragment()
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
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    private fun favouriteList() {
        lifecycleScope.launch {
            try {
                homeFragment.vacanciesList.forEach {
                    if (it.isFavorite) {
                        favouriteVacancy.clear()
                        favouriteVacancy.add(it)
                    }
                    binding.vacancyCount.text =
                        homeFragment.getVacancyPluralForm(viewModel.allLikeVacancy.value.size)
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
                    homeFragment.vacanciesList.forEach { vacancy ->
                        if (likeVacancies.any { it.id == vacancy.id && it.isFavorite }) {
                            if (!favouriteVacancy.contains(vacancy)) {
                                vacancy.isFavorite = true
                                favouriteVacancy.add(vacancy)
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                    binding.vacancyCount.text =
                        homeFragment.getVacancyPluralForm(favouriteVacancy.size)
                }
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
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