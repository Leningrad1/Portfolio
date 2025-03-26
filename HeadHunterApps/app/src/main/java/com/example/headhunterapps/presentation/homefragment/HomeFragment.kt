package com.example.headhunterapps.presentation.homefragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.headhunterapp.data.repository.Repository
import com.example.headhunterapp.model.Vacancy
import com.example.headhunterapps.R
import com.example.headhunterapps.presentation.ViewModel
import com.example.headhunterapps.databinding.FragmentHomeBinding
import com.example.headhunterapps.presentation.recyclerviewadapter.JobTitleRecyclerViewAdapter
import com.example.headhunterapps.presentation.recyclerviewadapter.VacancyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {
    @Inject
    lateinit var repository: Repository
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: ViewModel by viewModels()
    private var vacancyCount = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                tabButton()
                viewModel.addFavouriteVacancy()
                recyclerVacancy()
            }
        } catch (e: Throwable) {
        }
    }

    private fun recyclerVacancy() {
        lifecycleScope.launch {
            try {
                val recyclerView = binding.recyclerJobTitle
                val adapter =
                    VacancyAdapter(requireContext(), viewModel.vacancyFilter, ::onItemClick, viewModel)
                if (repository.offersList.isEmpty()) {
                    recyclerView.visibility = View.GONE
                } else {
                    recyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    recyclerView.adapter = JobTitleRecyclerViewAdapter(repository.offersList)
                }
                val recyclerViewVacancy = binding.recyclerVacancys
                if (viewModel.vacanciesList.isEmpty()) {
                    recyclerViewVacancy.visibility = View.GONE
                } else {
                    recyclerViewVacancy.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    recyclerViewVacancy.adapter = adapter

                }
                //Обработчик появление кнопки Еще...
                recyclerViewVacancy.addOnScrollListener(object :
                    RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val firstVisiblePosition =
                            layoutManager.findFirstCompletelyVisibleItemPosition()
                        if (firstVisiblePosition >= 1) {
                            showMoreButtonIfNeeded(viewModel.vacanciesList - viewModel.vacancyFilter.toSet())
                        } else {
                            binding.searchButton.visibility = View.INVISIBLE
                        }
                    }
                })
                viewModel.favoriteVacancies.collect { likeVacancies ->
                    // Обновляем список вакансий с учетом избранных
                    viewModel.vacancyFilter.forEach { vacancy ->
                        vacancy.isFavorite =
                            likeVacancies.any { it.id == vacancy.id && it.isFavorite }
                        Log.d("vacancy", "${viewModel.vacanciesList}")
                        if (viewModel.allLikeVacancy.value.isNotEmpty()) {
                            binding.redTab.visibility = View.VISIBLE
                            binding.textRed.visibility = View.VISIBLE
                            binding.textRed.text = viewModel.allLikeVacancy.value.size.toString()
                            Log.d("vacancyListMut", "${viewModel.allLikeVacancy}")
                        }
                        Log.d("vacancyListMut2", "${viewModel.allLikeVacancy.value}")
                    }
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun showMoreButtonIfNeeded(vacancies: List<Vacancy>) {
        try {
            lifecycleScope.launch {
                val remainingVacancies = vacancies.size
                val fragment = HomeFragment()
                val bundle = Bundle()
                if (remainingVacancies > 0) {
                    binding.searchButton.apply {
                        alpha = 0f
                        visibility = View.VISIBLE
                        animate().alpha(1f).setDuration(300).start()
                        text = "Ещё ${viewModel.getVacancyPluralForm(remainingVacancies)}"
                        vacancyCount = viewModel.getVacancyPluralForm(remainingVacancies)
                        setOnClickListener {
                            bundle.putString(VACANCY_ALL, viewModel.vacanciesList.size.toString())
                            fragment.arguments = bundle
                            findNavController().navigate(
                                R.id.action_homeFragment_to_vacancyAllFragment,
                                bundle
                            )
                        }
                    }
                } else {
                    binding.searchButton.animate().alpha(0f).setDuration(300).withEndAction {
                        binding.searchButton.visibility = View.GONE
                    }
                }
            }
        } catch (e: Throwable) {
        }
    }


    private fun onItemClick() {
        TODO("ЗАГЛУШКА")
    }

    private fun tabButton() {
        lifecycleScope.launch {
            binding.profile.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_entryEmailFragment
                )
            }
            binding.favourites.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_favoritesFragment
                )
            }
            binding.send.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_responseFragment
                )
            }
            binding.massage.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_massageFragment
                )
            }
        }
    }

    companion object {
        const val VACANCY_ALL = "vac_all"
    }
}