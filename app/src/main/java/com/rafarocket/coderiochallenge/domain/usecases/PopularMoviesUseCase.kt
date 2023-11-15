package com.rafarocket.coderiochallenge.domain.usecases

import arrow.core.Either
import com.rafarocket.coderiochallenge.data.MoviesRepository
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityPopular
import com.rafarocket.coderiochallenge.data.database.entities.toMoviesEntityPopular
import com.rafarocket.coderiochallenge.domain.model.Error
import com.rafarocket.coderiochallenge.domain.model.Movies
import javax.inject.Inject

class PopularMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Either<Error, Movies> {
        val movies = moviesRepository.getApiPopularMovies()

        return if (movies.isRight()) {
            moviesRepository.clearPopularMovies(
                movies.orNull()?.toMoviesEntityPopular() as MoviesEntityPopular
            )
            moviesRepository.insertPopularMovies(
                movies.orNull()?.toMoviesEntityPopular() as MoviesEntityPopular
            )
            movies
        } else {
            moviesRepository.getRoomPopularMovies()
        }
    }

}