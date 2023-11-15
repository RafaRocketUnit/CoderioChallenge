package com.rafarocket.coderiochallenge.data

import com.rafarocket.coderiochallenge.domain.model.Movies
import com.rafarocket.coderiochallenge.domain.model.PopularPerson
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface ApiService {


    // Movies
    @GET("movie/popular")
    suspend fun getPopularMovies(@HeaderMap headers: Map<String, String>): Movies

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@HeaderMap headers: Map<String, String>): Movies

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@HeaderMap headers: Map<String, String>): Movies


// Person
    @GET("person/popular")
    suspend fun getPopularPerson(@HeaderMap headers: Map<String, String>): PopularPerson

}