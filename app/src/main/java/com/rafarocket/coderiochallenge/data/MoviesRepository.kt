package com.rafarocket.coderiochallenge.data

import arrow.core.Either
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityPopular
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityTopRated
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityUpcoming
import com.rafarocket.coderiochallenge.domain.model.Error
import com.rafarocket.coderiochallenge.domain.model.Movies

interface MoviesRepository {

    //Popular
    suspend fun getApiPopularMovies(): Either<Error, Movies>
    suspend fun getRoomPopularMovies(): Either<Error, Movies>
    suspend fun insertPopularMovies(moviesData: MoviesEntityPopular)
    suspend fun clearPopularMovies(moviesData: MoviesEntityPopular)

    //Top Rated
    suspend fun getApiTopRatedMovies(): Either<Error, Movies>
    suspend fun getRoomTopRatedMovies(): Either<Error, Movies>
    suspend fun insertTopRatedMovies(moviesData: MoviesEntityTopRated)
    suspend fun clearTopRatedMovies(moviesData: MoviesEntityTopRated)

    //Upcoming
    suspend fun getApiUpcomingMovies(): Either<Error, Movies>
    suspend fun getRoomUpcomingMovies(): Either<Error, Movies>
    suspend fun insertUpcomingMovies(moviesData: MoviesEntityUpcoming)
    suspend fun clearUpcomingMovies(moviesData: MoviesEntityUpcoming)

}