package com.example.headhunterapp.presentation.homefragment

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.RecyclerView
import com.example.headhunterapp.R
import com.example.headhunterapp.data.repository.Repository
import com.example.headhunterapp.databinding.FragmentHomeBinding
import com.example.headhunterapp.dataclass.Vacancy
import com.example.headhunterapp.presentation.ViewModel
import com.example.headhunterapp.presentation.recyclerview.JobTitleRecyclerViewAdapter
import com.example.headhunterapp.presentation.recyclerview.VacancyAdapter
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val repository = Repository()
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: ViewModel by viewModels()
    var vacanciesList: MutableList<Vacancy> = repository.vacanciesListAll.toMutableList()
    private var vacancyFilter = vacanciesList.filterIndexed { index, _ -> index < 3 }
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
                addFavouriteVacancy()
                recyclerVacancy()
            }
        } catch (e: Throwable) {
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    private fun recyclerVacancy() {
        lifecycleScope.launch {
            try {
                val recyclerView = binding.recyclerJobTitle
                val adapter =
                    VacancyAdapter(requireContext(), vacancyFilter, ::onItemClick, viewModel)
                if (repository.offersList.isEmpty()) {
                    recyclerView.visibility = View.GONE
                } else {
                    recyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    recyclerView.adapter = JobTitleRecyclerViewAdapter(repository.offersList)
                }
                val recyclerViewVacancy = binding.recyclerVacancys
                if (vacanciesList.isEmpty()) {
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
                            showMoreButtonIfNeeded(vacanciesList - vacancyFilter.toSet())
                        } else {
                            binding.searchButton.visibility = View.INVISIBLE
                        }
                    }
                })
                viewModel.favoriteVacancies.collect { likeVacancies ->
                    // Обновляем список вакансий с учетом избранных
                    vacancyFilter.forEach { vacancy ->
                        vacancy.isFavorite =
                            likeVacancies.any { it.id == vacancy.id && it.isFavorite }
                        Log.d("vacancy", "$vacanciesList")
                    }
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
            }
        }
    }

    fun addFavouriteVacancy() {
        lifecycleScope.launch {
            vacanciesList.forEach { it ->
                if (it.isFavorite) {
                    viewModel.addFavouriteVacancy(it)
                }
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
                        text = "Ещё ${getVacancyPluralForm(remainingVacancies)}"
                        vacancyCount = getVacancyPluralForm(remainingVacancies)
                        setOnClickListener {
                            bundle.putString(VACANCY_ALL, vacanciesList.size.toString())
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
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    fun getVacancyPluralForm(count: Int): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> "$count вакансия"
            count % 10 in 2..4 && count % 100 !in 12..14 -> "$count вакансии"
            else -> "$count вакансий"
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