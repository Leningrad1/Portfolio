package com.example.cinemapark.data.retrofit

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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
/*
private const val API_KEY = "628dff66-4845-48c8-91b3-76094ae63015"
*/
/*
private const val API_KEY = "ef5273e8-e9fb-4549-a787-d0d197c0ff55"
*/
/*
private const val API_KEY = "1f082b4f-d6af-4f02-a73f-de15d980162f"
*/
/*
private const val API_KEY = "ae5f546d-9ee5-4716-aa9c-dec3f09ee9ac"
*/
/*
private const val API_KEY = "7d124dbf-21d8-4a37-ad7b-a0dd3c06a5b6"
*/
/*
private const val API_KEY = "b55d578a-4023-4696-9d29-a3c7badb716e"
*/
/*
private const val API_KEY = "4d6d6959-b9eb-4906-aac1-44684e6a8b50"
*/
/*
private const val API_KEY = "19adbd25-ec4c-4c26-9aa6-0529f4888f51"
*/
/*
private const val API_KEY = "fc96c731-a0fc-4bce-bcc2-8ec593773eb9"
*/
private const val API_KEY = "44ab0df2-7e64-480f-afd3-ab2439e9c5ac"

object RetrofitObj {
    private val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()


    val retrofitApi: AdapterApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(AdapterApi::class.java)
}

interface AdapterApi {

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/premieres")
    suspend fun getPremierInfo(
        @Query("year") year: Int,
        @Query("month") month: String,
    ): Premier

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/collections?type=COMICS_THEME") // collections?type=TOP_POPULAR_ALL и collections?type=TOP_POPULAR_MOVIES
    //эти ссылки на поп фильмы не работают из того же списка взял другую
    suspend fun getPopularInfo(
        @Query("page") page: Int
    ): Popular

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films?countries=1&genres=11&order=RATING&type=FILM&ratingFrom=5.0&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getUsaMilitantsInfo(
        @Query("page") page: Int
    ): Action

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/collections?type=TOP_250_MOVIES")
    suspend fun getTopInfo(
        @Query("page") page: Int
    ): Top

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films?countries=3&genres=2&order=RATING&type=FILM&ratingFrom=8.0&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getDrameFranceInfo(
        @Query("page") page: Int
    ): Drame

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films?countries=1&genres=1&order=RATING&type=TV_SERIES&ratingFrom=5&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getSeriasInfo(
        @Query("page") page: Int
    ): Serias

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/collections?type=TOP_250_MOVIES")
    suspend fun getTestInfo(
        @Query("page") page: Int
    ): Top

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}")
    suspend fun getFilmId(
        @Path("id") id: Int
    ): FilmPage

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}")
    suspend fun getFilmIdActor(
        @Path("id") id: Int
    ): com.example.cinemapark.dataclass.actorpage2.FilmPage

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v1/staff/{id}")
    suspend fun getActorId(
        @Path("id") id: Int
    ): ActorPage

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v1/staff")
    suspend fun getRoleInfo(
        @Query("filmId") filmId: Int
    ): List<RoleItem>

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/images?type=STILL")
    suspend fun getGallery(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): Gallery

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/images?type=POSTER")
    suspend fun getGalleryPost(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): GalleryPost

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/images?type=SCREENSHOT")
    suspend fun getGalleryFrame(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): GalleryFrame

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/similars")
    suspend fun getSimilar(
        @Path("id") id: Int
    ): Similar

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/seasons")
    suspend fun getSeasons(
        @Path("id") id: Int
    ): Seasons

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/filters")
    suspend fun getGenreCountry(
    ): GenreCountry

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.1/films/search-by-keyword")
    suspend fun getSearchKeyWord(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): SearchKeyWord

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films")
    suspend fun getSearchParam(
        @Query("countries") countries: Int,
        @Query("genres") genres: Int,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("page") page: Int
    ): SearchParam
}