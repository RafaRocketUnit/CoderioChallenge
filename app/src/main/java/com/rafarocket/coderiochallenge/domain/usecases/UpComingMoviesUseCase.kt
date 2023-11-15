package com.rafarocket.coderiochallenge.domain.usecases

import arrow.core.Either
import com.rafarocket.coderiochallenge.data.MoviesRepository
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityUpcoming
import com.rafarocket.coderiochallenge.data.database.entities.toMoviesEntityUpcoming
import com.rafarocket.coderiochallenge.domain.model.Error
import com.rafarocket.coderiochallenge.domain.model.Movies
import javax.inject.Inject

class UpComingMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Either<Error, Movies> {
        val movies = moviesRepository.getApiUpcomingMovies()

        return if (movies.isRight()) {
            moviesRepository.clearUpcomingMovies(
                movies.orNull()?.toMoviesEntityUpcoming() as MoviesEntityUpcoming
            )
            moviesRepository.insertUpcomingMovies(
                movies.orNull()?.toMoviesEntityUpcoming() as MoviesEntityUpcoming
            )
            movies
        } else {
            moviesRepository.getRoomUpcomingMovies()
        }
    }

}