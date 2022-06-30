package com.example.moviesappcat25.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappcat25.domain.mapToDomainMovie
import com.example.moviesappcat25.rest.MoviesRepository
import com.example.moviesappcat25.utils.FailureResponseException
import com.example.moviesappcat25.utils.IntentViewAction
import com.example.moviesappcat25.utils.ResponseBodyNullException
import com.example.moviesappcat25.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _movies: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val movies: LiveData<UIState> get() = _movies

    private val _movieDetails: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val movieDetails: LiveData<UIState> get() = _movieDetails

    fun intentActionToGetData(intentView: IntentViewAction) {
        when (intentView) {
            is IntentViewAction.RetrievePlayingNowMovies -> {
                retrievePlayingNowMovies(intentView.page)
            }
            is IntentViewAction.RetrievePopularMovies -> {
                retrievePopularMovies(intentView.page)
            }
            is IntentViewAction.RetrieveUpcomingMovies -> {
                retrieveUpcomingMovies(intentView.page)
            }
            is IntentViewAction.RetrieveMovieDetails -> {
                retrieveMovieDetails(intentView.movieId)
            }
        }

    }

    private fun retrievePlayingNowMovies(page: Int) {
        viewModelScope.launch(ioDispatcher) {
            moviesRepository.getPlayingNowMovies(page).collect {
                _movies.postValue(it)
            }
        }
    }

    private fun retrieveMovieDetails(movieId: Int) {
        _movieDetails.postValue(UIState.LOADING)

        viewModelScope.launch(ioDispatcher) {
            val uiState = moviesRepository.getMovieDetails(movieId, this)
            _movieDetails.postValue(uiState)
        }
    }

    private fun retrieveUpcomingMovies(page: Int) {
        _movies.postValue(UIState.LOADING)

        viewModelScope.launch(ioDispatcher) {
            try {
                val response = moviesRepository.getUpcomingMovies(page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _movies.postValue(UIState.SUCCESS(it.results.mapToDomainMovie()))
                    } ?: throw ResponseBodyNullException("Upcoming movies")
                } else {
                    throw FailureResponseException("Upcoming movies")
                }
            } catch (e: Exception) {
                _movies.postValue(UIState.ERROR(e))
            }
        }
    }

    private fun retrievePopularMovies(page: Int) {
        viewModelScope.launch(ioDispatcher) {
            moviesRepository.getPopularMovies(page).collect {
                _movies.postValue(it)
            }
        }
    }
}