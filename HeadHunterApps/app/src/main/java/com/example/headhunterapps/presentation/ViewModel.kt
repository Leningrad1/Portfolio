package com.example.headhunterapps.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.headhunterapp.data.db.UserDao
import com.example.headhunterapp.data.repository.Repository
import com.example.headhunterapp.model.Vacancy
import com.example.headhunterapp.model.dbdata.LikeVacancy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val myDao: UserDao,
    private val repository: Repository
) : ViewModel() {

    private val _favoriteVacancies = MutableStateFlow<List<LikeVacancy>>(emptyList())
    val favoriteVacancies: StateFlow<List<LikeVacancy>> = _favoriteVacancies
    var vacanciesList: MutableList<Vacancy> = repository.vacanciesListAll.toMutableList()
    var vacancyFilter = vacanciesList.filterIndexed { index, _ -> index < 3 }

    init {
        viewModelScope.launch {
            allLikeVacancy.collect { likeVacancies ->
                _favoriteVacancies.value = likeVacancies
            }
        }
    }

    val allLikeVacancy = myDao.getAllVacancy()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun onAddLikeVacancy(
        idVacancy: String,
        likeVacancy: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingLikeVacancy = allLikeVacancy.value.find { it.id == idVacancy }

            if (existingLikeVacancy == null) {
                val userLikeVacancy = LikeVacancy(
                    id = idVacancy,
                    isFavorite = likeVacancy
                )
                myDao.insert(userLikeVacancy)
                Log.d("mutAdd", "$userLikeVacancy")
                Log.d("mutAll", "${allLikeVacancy.value}")
            } else {
                myDao.insert(existingLikeVacancy.copy(isFavorite = likeVacancy))
                Log.d("mutUpdate", "Updated: $existingLikeVacancy")
            }
        }
    }

    fun onRemoveLikeVacancy(
        idVacancy: String,
        likeVacancy: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val userLikeVacancy = LikeVacancy(
                id = idVacancy,
                isFavorite = likeVacancy
            )
            myDao.deleteMovieId(userLikeVacancy)
            Log.d("mutDel", "$userLikeVacancy")
            Log.d("mutAll", "${allLikeVacancy.value}")
        }
    }

    fun getVacancyPluralForm(count: Int): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> "$count вакансия"
            count % 10 in 2..4 && count % 100 !in 12..14 -> "$count вакансии"
            else -> "$count вакансий"
        }
    }
    fun addFavouriteVacancy() {
        viewModelScope.launch {
            vacanciesList.forEach { it ->
                if (it.isFavorite) {
                    onAddLikeVacancy(it.id, it.isFavorite)
                }
            }
        }
    }
}