package com.example.moviesappcat25.rest

import com.example.moviesappcat25.model.MovieResponse
import com.example.moviesappcat25.model.moviedetails.DetailsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET(PLAYING_NOW_PATH)
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE,
        @Query("api_key") key: String = API_KEY
    ): Response<MovieResponse>

    @GET(POPULAR_PATH)
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE,
        @Query("api_key") key: String = API_KEY
    ): Response<MovieResponse>

    @GET(UPCOMING_PATH)
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE,
        @Query("api_key") key: String = API_KEY
    ): Response<MovieResponse>

    @GET(MOVIE_DETAILS_PATH)
    fun getMovieDetailsById(
        @Path("movieId") movieId: Int,
        @Query("language") language: String = LANGUAGE,
        @Query("api_key") key: String = API_KEY
    ): Deferred<Response<DetailsResponse>>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        private const val POPULAR_PATH = "popular"
        private const val PLAYING_NOW_PATH = "now_playing"
        private const val UPCOMING_PATH = "upcoming"
        private const val MOVIE_DETAILS_PATH = "{movieId}"

        private const val LANGUAGE = "en-US"
        private const val API_KEY = "819950d4cf35be1fb70d8746bc0796bf"
    }
}