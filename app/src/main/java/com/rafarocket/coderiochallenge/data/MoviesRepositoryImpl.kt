package com.rafarocket.coderiochallenge.data

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.rafarocket.coderiochallenge.tokenAuthBearerTMDB
import com.rafarocket.coderiochallenge.data.database.dao.MoviesDao
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityPopular
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityTopRated
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityUpcoming
import com.rafarocket.coderiochallenge.domain.model.Error
import com.rafarocket.coderiochallenge.domain.model.Movies
import com.rafarocket.coderiochallenge.domain.model.toMovies
import retrofit2.HttpException
import java.lang.IllegalStateException
import java.net.UnknownHostException
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val moviesDao: MoviesDao
    ): MoviesRepository {

    private val headers = mutableMapOf<String, String>()

    override suspend fun getApiPopularMovies(): Either<Error, Movies> {
        headers["Authorization"] = tokenAuthBearerTMDB
        return try {
            val result = apiService.getPopularMovies(headers) ?: null
            (result as Movies).right()
        } catch (exp: HttpException) {
            Error(exp.code(), exp.message as String, false).left()
        } catch (exception: UnknownHostException) {
            Error(exception.hashCode(), exception.message as String, false).left()
        }
    }

    override suspend fun getApiTopRatedMovies(): Either<Error, Movies> {
        headers["Authorization"] = tokenAuthBearerTMDB

        return try {
            val result = apiService.getTopRatedMovies(headers)
            result.right()
        } catch (exp: HttpException) {
            Error(exp.code(), exp.message as String, false).left()
        } catch (exception: UnknownHostException) {
            Error(exception.hashCode(), exception.message as String, false).left()
        }
    }

    override suspend fun getApiUpcomingMovies(): Either<Error, Movies> {
        headers["Authorization"] = tokenAuthBearerTMDB

        return try {
            val result = apiService.getUpcomingMovies(headers)
            result.right()
        } catch (exp: HttpException) {
            Error(exp.code(), exp.message as String, false).left()
        } catch (exception: UnknownHostException) {
            Error(exception.hashCode(), exception.message as String, false).left()
        }
    }

    override suspend fun getRoomPopularMovies(): Either<Error, Movies> {
       return try {
            moviesDao.getPopularMovies().toMovies().right()
        } catch (exception: IllegalStateException) {
           Error(exception.hashCode(), exception.message as String, false).left()
        }

    }

    override suspend fun getRoomTopRatedMovies(): Either<Error, Movies> {
        return try {
            moviesDao.getTopRatedMovies().toMovies().right()
        } catch (exception: IllegalStateException) {
            Error(exception.hashCode(), exception.message as String, false).left()
        }

    }

    override suspend fun getRoomUpcomingMovies(): Either<Error, Movies> {
        return try {
            moviesDao.getUpcomingMovies().toMovies().right()
        } catch (exception: IllegalStateException) {
            Error(exception.hashCode(), exception.message as String, false).left()
        }
    }

    override suspend fun insertPopularMovies(moviesData: MoviesEntityPopular) {
        try {
            moviesDao.insertPopularMovies(moviesData)
        } catch (exp: IllegalStateException) {
            exp.printStackTrace()
        }
    }

    override suspend fun insertTopRatedMovies(moviesData: MoviesEntityTopRated) {
        try {
            moviesDao.insertTopRated(moviesData)
        } catch (exp: IllegalStateException) {
            exp.printStackTrace()
        }
    }

    override suspend fun insertUpcomingMovies(moviesData: MoviesEntityUpcoming) {
        try {
            moviesDao.insertUpcoming(moviesData)
        } catch (exp: IllegalStateException) {
            exp.printStackTrace()
        }
    }

    override suspend fun clearPopularMovies(moviesData: MoviesEntityPopular) {
        try {
            moviesDao.deletePopularMovies(moviesData)
        } catch (exp: IllegalStateException) {
            exp.printStackTrace()
        }
    }

    override suspend fun clearTopRatedMovies(moviesData: MoviesEntityTopRated) {
        try {
            moviesDao.deleteTopRated(moviesData)
        } catch (exp: IllegalStateException) {
            exp.printStackTrace()
        }
    }

    override suspend fun clearUpcomingMovies(moviesData: MoviesEntityUpcoming) {
        try {
            moviesDao.deleteUpcoming(moviesData)
        } catch (exp: IllegalStateException) {
            exp.printStackTrace()
        }
    }
}