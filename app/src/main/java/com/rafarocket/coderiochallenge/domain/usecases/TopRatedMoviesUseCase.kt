package com.rafarocket.coderiochallenge.domain.usecases

import arrow.core.Either
import com.rafarocket.coderiochallenge.data.MoviesRepository
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityTopRated
import com.rafarocket.coderiochallenge.data.database.entities.toMoviesEntityTopRated
import com.rafarocket.coderiochallenge.domain.model.Error
import com.rafarocket.coderiochallenge.domain.model.Movies
import javax.inject.Inject

class TopRatedMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(): Either<Error, Movies> {
        val movies = moviesRepository.getApiTopRatedMovies()
        return if (movies.isRight()) {
            moviesRepository.clearTopRatedMovies(
                movies.orNull()?.toMoviesEntityTopRated() as MoviesEntityTopRated
            )
            moviesRepository.insertTopRatedMovies(
                movies.orNull()?.toMoviesEntityTopRated() as MoviesEntityTopRated
            )
            movies
        } else {
            moviesRepository.getRoomTopRatedMovies()
        }
    }
}