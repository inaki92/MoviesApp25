package com.example.moviesappcat25.di

import com.example.moviesappcat25.MainActivity
import com.example.moviesappcat25.ui.PopularMoviesFragment
import dagger.Component

@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface MovieComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(popularMoviesFragment: PopularMoviesFragment)
}