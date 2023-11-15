package com.rafarocket.coderiochallenge.data.di

import com.rafarocket.coderiochallenge.data.ApiService
import com.rafarocket.coderiochallenge.data.MoviesRepository
import com.rafarocket.coderiochallenge.data.MoviesRepositoryImpl
import com.rafarocket.coderiochallenge.data.PersonRepository
import com.rafarocket.coderiochallenge.data.PersonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
interface ApiModule {

    @Binds
    fun MoviesRepositoryImpl.bindMoviesRepository(): MoviesRepository

    @Binds
    fun PersonRepositoryImpl.bindPersonRepository(): PersonRepository

    companion object {

        private const val BASE_URL = "https://api.themoviedb.org/3/"

        @Provides
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        fun providesApiServices(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }

}