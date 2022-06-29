package com.example.moviesappcat25.domain

import com.example.moviesappcat25.model.moviedetails.DetailsResponse
import com.example.moviesappcat25.model.moviedetails.Genre

data class DomainMovieDetails(
    val id: Int,
    val genres: List<Genre>,
    val title: String,
    val overview: String,
    val posterPath: String,
    val website: String,
    val runtime: Int,
    val releaseDate: String
)

fun DetailsResponse.mapToDomainMovieDetails(): DomainMovieDetails =
    DomainMovieDetails(
        id = this.id ?: 99999,
        genres = this.genres ?: emptyList(),
        title = this.title ?: "NO TITLE",
        overview = this.overview ?: "NO OVERVIEW AVAILABLE",
        posterPath = this.posterPath ?: "NO POSTER",
        website = this.homepage ?: "NO WEBSITE",
        runtime = this.runtime ?: 0,
        releaseDate = this.releaseDate ?: "NO RELEASED"
    )
