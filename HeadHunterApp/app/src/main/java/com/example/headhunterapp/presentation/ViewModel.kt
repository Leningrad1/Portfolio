package com.example.headhunterapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.headhunterapp.data.db.DaoRepository
import com.example.headhunterapp.dataclass.Vacancy
import com.example.headhunterapp.dataclass.dbdataclass.LikeVacancy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    private val mutableFavouriteCollect = mutableListOf<Vacancy>()
    private val _favoriteVacancies = MutableStateFlow<List<LikeVacancy>>(emptyList())
    val favoriteVacancies: StateFlow<List<LikeVacancy>> = _favoriteVacancies

    init {
        viewModelScope.launch {
            allLikeVacancy.collect { likeVacancies ->
                _favoriteVacancies.value = likeVacancies
            }
        }
    }

    fun addFavouriteVacancy(vacancy: Vacancy) {
        viewModelScope.launch {
            mutableFavouriteCollect.add(vacancy)
        }
    }

    private val daoRepository = DaoRepository()
    val allLikeVacancy = daoRepository.getAllVacancyLike()
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
                daoRepository.insertLikeVacancy(userLikeVacancy)
                Log.d("mutAdd", "$userLikeVacancy")
                Log.d("mutAll", "${allLikeVacancy.value}")
            } else {
                daoRepository.insertLikeVacancy(existingLikeVacancy.copy(isFavorite = likeVacancy))
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
            daoRepository.deleteLikeVacancy(userLikeVacancy)
            Log.d("mutDel", "$userLikeVacancy")
            Log.d("mutAll", "${allLikeVacancy.value}")
        }
    }
}