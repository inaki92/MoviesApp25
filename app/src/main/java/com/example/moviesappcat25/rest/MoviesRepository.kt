package com.example.moviesappcat25.rest

import com.example.moviesappcat25.domain.mapToDomainMovie
import com.example.moviesappcat25.domain.mapToDomainMovieDetails
import com.example.moviesappcat25.model.MovieResponse
import com.example.moviesappcat25.utils.FailureResponseException
import com.example.moviesappcat25.utils.ResponseBodyNullException
import com.example.moviesappcat25.utils.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response

interface MoviesRepository {
    fun getPlayingNowMovies(page: Int = 1): Flow<UIState>
    suspend fun getUpcomingMovies(page: Int = 1): Response<MovieResponse>
    fun getPopularMovies(page: Int = 1): Flow<UIState>
    suspend fun getMovieDetails(movieId: Int): UIState
}

class MoviesRepositoryImpl(
    private val moviesApi: MoviesService
) : MoviesRepository {

    override fun getPlayingNowMovies(page: Int): Flow<UIState> =
        flow {
            emit(UIState.LOADING)

            try {
                val response = moviesApi.getNowPlayingMovies(page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(UIState.SUCCESS(it.results.mapToDomainMovie()))
                    } ?: throw ResponseBodyNullException(ERROR_PLAYING_NOW)
                } else {
                    throw FailureResponseException(ERROR_PLAYING_NOW)
                }
            } catch (e: Exception) {
                emit(UIState.ERROR(e))
            }
        }

    override suspend fun getUpcomingMovies(page: Int): Response<MovieResponse> =
        moviesApi.getUpcomingMovies(page)

    override fun getPopularMovies(page: Int): Flow<UIState> =
        flow {
            emit(UIState.LOADING)

            try {
                val response = moviesApi.getPopularMovies(page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(UIState.SUCCESS(it.results.mapToDomainMovie()))
                    } ?: throw ResponseBodyNullException(ERROR_POPULAR)
                } else {
                    throw FailureResponseException(ERROR_POPULAR)
                }
            } catch (e: Exception) {
                emit(UIState.ERROR(e))
            }
        }

    override suspend fun getMovieDetails(movieId: Int): UIState {
        val uiState = CoroutineScope(Dispatchers.IO).async {
            try {
                val response = moviesApi.getMovieDetailsById(movieId).await()
                if (response.isSuccessful) {
                    response.body()?.let {
                        return@async UIState.SUCCESS(it.mapToDomainMovieDetails())
                    } ?: throw ResponseBodyNullException(ERROR_POPULAR)
                } else {
                    throw FailureResponseException(ERROR_POPULAR)
                }
            } catch (e: Exception) {
                return@async UIState.ERROR(e)
            }
        }.await()

        return uiState
    }

    companion object {
        private const val ERROR_PLAYING_NOW = "PLAYING NOW MOVIES"
        private const val ERROR_POPULAR = "POPULAR MOVIES"
    }
}