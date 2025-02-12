package com.example.cinemapark.data.retrofit

import android.util.Log
import com.example.cinemapark.dataclass.action.Action
import com.example.cinemapark.dataclass.actorpage.ActorPage
import com.example.cinemapark.dataclass.drame.Drame
import com.example.cinemapark.dataclass.eTop.Top
import com.example.cinemapark.dataclass.filmpage.FilmPage
import com.example.cinemapark.dataclass.gallery.Gallery
import com.example.cinemapark.dataclass.galleryframe.GalleryFrame
import com.example.cinemapark.dataclass.gallerypost.GalleryPost
import com.example.cinemapark.dataclass.genrecountry.GenreCountry
import com.example.cinemapark.dataclass.popular.Popular
import com.example.cinemapark.dataclass.premier.Premier
import com.example.cinemapark.dataclass.role.RoleItem
import com.example.cinemapark.dataclass.searchkeyword.SearchKeyWord
import com.example.cinemapark.dataclass.searchparam.SearchParam
import com.example.cinemapark.dataclass.seasons.Seasons
import com.example.cinemapark.dataclass.serias.Serias
import com.example.cinemapark.dataclass.similar.Similar

class FilmRepository {
    suspend fun getFilmPremierInfo(year: Int, month: String): Premier {
        return RetrofitObj.retrofitApi.getPremierInfo(year, month)
    }

    suspend fun getFilmPopularInfo(page: Int): Popular {
        return RetrofitObj.retrofitApi.getPopularInfo(page)
    }

    suspend fun getFilmUsaMilitantsInfo(page: Int): Action {
        return RetrofitObj.retrofitApi.getUsaMilitantsInfo(page)
    }

    suspend fun getFilmTopInfo(page: Int): Top {
        return RetrofitObj.retrofitApi.getTopInfo(page)
    }

    suspend fun getFilmDrameFranceInfo(page: Int): Drame {
        return RetrofitObj.retrofitApi.getDrameFranceInfo(page)
    }

    suspend fun getSeriasInfo(page: Int): Serias {
        return RetrofitObj.retrofitApi.getSeriasInfo(page)
    }

    suspend fun getTest(page: Int): Top {
        return RetrofitObj.retrofitApi.getTestInfo(page)
    }

    suspend fun getFilmPageId(id: Int): FilmPage {
        return RetrofitObj.retrofitApi.getFilmId(id)
    }

    suspend fun getFilmPageIdActor(id: Int): com.example.cinemapark.dataclass.actorpage2.FilmPage {
        return RetrofitObj.retrofitApi.getFilmIdActor(id)
    }

    suspend fun getRoleInfo(filmId: Int): List<RoleItem> {
        return RetrofitObj.retrofitApi.getRoleInfo(filmId)
    }

    suspend fun getGalleryId(id: Int, page: Int): Gallery {
        return RetrofitObj.retrofitApi.getGallery(id, page)
    }

    suspend fun getGalleryIdPost(id: Int, page: Int): GalleryPost {
        return RetrofitObj.retrofitApi.getGalleryPost(id, page)
    }

    suspend fun getActorPage(id: Int): ActorPage {
        return RetrofitObj.retrofitApi.getActorId(id)
    }

    suspend fun getGalleryIdFrame(id: Int, page: Int): GalleryFrame {
        return RetrofitObj.retrofitApi.getGalleryFrame(id, page)
    }

    suspend fun getSimilarId(id: Int): Similar {
        return RetrofitObj.retrofitApi.getSimilar(id)
    }

    suspend fun getSeasonsId(id: Int): Seasons {
        return RetrofitObj.retrofitApi.getSeasons(id)
    }

    suspend fun getSearchKeyWord(keyWord: String, page: Int): SearchKeyWord {
        return RetrofitObj.retrofitApi.getSearchKeyWord(keyWord, page)
    }

    suspend fun getGenreCountry(): GenreCountry {
        return RetrofitObj.retrofitApi.getGenreCountry()
    }
    suspend fun getSearchParam(
        countries: Int,
        genres: Int,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        page: Int
    ): SearchParam {
        return RetrofitObj.retrofitApi.getSearchParam(
            countries,
            genres,
            order,
            type,
            ratingFrom,
            ratingTo,
            yearFrom,
            yearTo,
            page
        )
    }
}