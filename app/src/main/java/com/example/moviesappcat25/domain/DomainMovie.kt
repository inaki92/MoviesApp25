package com.example.moviesappcat25.domain

import com.example.moviesappcat25.model.Movie

data class DomainMovie(
    val id: Int,
    val genreIds: List<Int>,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String
)

fun List<Movie>?.mapToDomainMovie(): List<DomainMovie> {
    return this?.let {
        it.map { remote ->
            DomainMovie(
                id = remote.id ?: 99999,
                genreIds = remote.genreIds ?: emptyList(),
                originalTitle = remote.originalTitle ?: "NO TITLE",
                overview = remote.overview ?: "No overview",
                popularity = remote.popularity ?: 0.0,
                posterPath = remote.posterPath ?: "INVALID POSTER",
                releaseDate = remote.releaseDate ?: "NO RELEASED"
            )
        }
    } ?: emptyList()
}
