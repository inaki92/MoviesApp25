package com.example.moviesappcat25

import android.app.Application
import com.example.moviesappcat25.di.ApplicationModule
import com.example.moviesappcat25.di.DaggerMovieComponent
import com.example.moviesappcat25.di.MovieComponent

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        moviesComponent = DaggerMovieComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    companion object {
        lateinit var moviesComponent: MovieComponent
    }
}