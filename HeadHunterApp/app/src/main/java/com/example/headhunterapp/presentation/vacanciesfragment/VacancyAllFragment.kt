package com.example.headhunterapp.presentation.vacanciesfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.headhunterapp.R
import com.example.headhunterapp.databinding.FragmentVacancyAllBinding
import com.example.headhunterapp.dataclass.Vacancy
import com.example.headhunterapp.presentation.ViewModel
import com.example.headhunterapp.presentation.homefragment.HomeFragment
import com.example.headhunterapp.presentation.homefragment.HomeFragment.Companion.VACANCY_ALL
import com.example.headhunterapp.presentation.recyclerview.VacancyAdapter
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.launch

class VacancyAllFragment : Fragment() {
    private lateinit var binding: FragmentVacancyAllBinding
    private val viewModel: ViewModel by viewModels()
    private val homeFragment = HomeFragment()
    var list: MutableList<Vacancy> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVacancyAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                tabButton()
                homeFragment.addFavouriteVacancy()
                val vacancyCount = bundle?.getString(VACANCY_ALL)
                binding.textIdVacancy.text =
                    homeFragment.getVacancyPluralForm(vacancyCount!!.toInt())
                val recyclerViewVacancy = binding.recyclerViewAllVacancy
                recyclerViewVacancy.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                viewModel.favoriteVacancies.collect { likeVacancies ->
                    list.clear()
                    homeFragment.vacanciesList.forEach { vacancy ->
                        vacancy.isFavorite =
                            likeVacancies.any { it.id == vacancy.id && it.isFavorite }
                        list.add(vacancy)
                        Log.d("likeVacancies333", "$list")
                    }
                    if (viewModel.allLikeVacancy.value.isNotEmpty()) {
                        binding.redTab.visibility = View.VISIBLE
                        binding.textRed.visibility = View.VISIBLE
                        binding.textRed.text = viewModel.allLikeVacancy.value.size.toString()
                        Log.d("vacancyListMut", "${viewModel.allLikeVacancy}")
                    }
                    Log.d("vacancyListMut2", "${viewModel.allLikeVacancy.value}")
                    if (recyclerViewVacancy.adapter == null) {
                        recyclerViewVacancy.adapter =
                            VacancyAdapter(requireContext(), list, ::onItemClick, viewModel)
                    } else {
                        recyclerViewVacancy.adapter?.notifyDataSetChanged()
                    }
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
                    R.id.action_vacancyAllFragment_to_entryEmailFragment
                )
            }
            binding.favourites.setOnClickListener {
                findNavController().navigate(
                    R.id.action_vacancyAllFragment_to_favoritesFragment
                )
            }
            binding.send.setOnClickListener {
                findNavController().navigate(
                    R.id.action_vacancyAllFragment_to_responseFragment
                )
            }
            binding.massage.setOnClickListener {
                findNavController().navigate(
                    R.id.action_vacancyAllFragment_to_massageFragment
                )
            }
            binding.imageBack.setOnClickListener {
                findNavController().navigate(
                    R.id.action_vacancyAllFragment_to_homeFragment
                )
            }
        }
    }
}