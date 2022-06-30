package com.example.moviesappcat25.utils

sealed class IntentViewAction {
    data class RetrievePopularMovies(val page: Int = 1): IntentViewAction()
    data class RetrievePlayingNowMovies(val page: Int = 1): IntentViewAction()
    data class RetrieveUpcomingMovies(val page: Int = 1): IntentViewAction()
    data class RetrieveMovieDetails(val movieId: Int): IntentViewAction()
}
