package com.example.moviesappcat25.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesappcat25.model.Movie
import com.example.moviesappcat25.rest.MoviesRepository
import com.example.moviesappcat25.rest.MoviesRepositoryImpl
import com.example.moviesappcat25.rest.MoviesService
import com.example.moviesappcat25.viewmodel.MoviesViewModel
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

//    @Provides
//    @Named("serviceOne")
//    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
//        Retrofit.Builder()
//            .baseUrl(MoviesService.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(okHttpClient)
//            .build()
//
//    @Provides
//    @Named("serviceTwo")
//    fun providesRetrofit2(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
//        Retrofit.Builder()
//            .baseUrl(MoviesService.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(okHttpClient)
//            .build()


    @Provides
    fun providesMoviesServiceApi(okHttpClient: OkHttpClient, gson: Gson): MoviesService =
        Retrofit.Builder()
            .baseUrl(MoviesService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(MoviesService::class.java)

    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun providesMovieRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository
}

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun providesContext(): Context = application.applicationContext
}

@Module
class ViewModelModule {

    @Provides
    fun providesMovieViewModelFactory(
        ioDispatcher: CoroutineDispatcher,
        moviesRepository: MoviesRepository
    ): ViewModelProvider.Factory =
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MoviesViewModel(moviesRepository, ioDispatcher) as T
            }
        }
}