package com.rafarocket.coderiochallenge.data

import arrow.core.right
import com.rafarocket.coderiochallenge.data.database.dao.MoviesDao
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityPopular
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityTopRated
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityUpcoming
import com.rafarocket.coderiochallenge.domain.model.Dates
import com.rafarocket.coderiochallenge.domain.model.Error
import com.rafarocket.coderiochallenge.domain.model.Movie
import com.rafarocket.coderiochallenge.domain.model.Movies
import com.rafarocket.coderiochallenge.domain.model.toMovies
import com.rafarocket.coderiochallenge.tokenAuthBearerTMDB
import io.mockk.coEvery
import io.mockk.coVerify
import org.junit.Test
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest

internal class MoviesRepositoryImplTest {

    private val serviceApi: ApiService = mockk(relaxed = true)
    private val movieDao: MoviesDao = mockk(relaxed = true)
    private val repositoryImpl = MoviesRepositoryImpl(serviceApi, movieDao)
    private val error = Error(0,"", false )
    private val headers = mutableMapOf<String, String>()

    @Test
    fun getApiPopularMovies()  = runTest {
        headers["Authorization"] = tokenAuthBearerTMDB
        coEvery {
            serviceApi.getPopularMovies(headers)
        } returns Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        )


        val expectedValue =  Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ).right()

        val result = repositoryImpl.getApiPopularMovies()

        coVerify { serviceApi.getPopularMovies(headers) }

        assertEquals(expectedValue, result)
    }

    @Test
    fun getApiTopRatedMovies() = runTest {
        headers["Authorization"] = tokenAuthBearerTMDB
        coEvery {
            serviceApi.getTopRatedMovies(headers)
        } returns Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        )


        val expectedValue =  Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ).right()

        val result = repositoryImpl.getApiTopRatedMovies()

        coVerify { serviceApi.getTopRatedMovies(headers) }

        assertEquals(expectedValue, result)
    }

    @Test
    fun getApiUpcomingMovies() = runTest {
        headers["Authorization"] = tokenAuthBearerTMDB
        coEvery {
            serviceApi.getUpcomingMovies(headers)
        } returns Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        )


        val expectedValue =  Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ).right()

        val result = repositoryImpl.getApiUpcomingMovies()

        coVerify { serviceApi.getUpcomingMovies(headers) }

        assertEquals(expectedValue, result)
    }

    @Test
    fun getRoomPopularMovies() = runTest {
        coEvery {
            movieDao.getPopularMovies()
        } returns MoviesEntityPopular(
            "",
            1,
            "",
            1,
            1
        )


        val expectedValue =  MoviesEntityPopular(
            "",
            1,
            "",
            1,
            1
        ).toMovies().right()

        val result = repositoryImpl.getRoomPopularMovies()

        coVerify { movieDao.getPopularMovies() }

        assertEquals(expectedValue, result)
    }

    @Test
    fun getRoomTopRatedMovies() = runTest {
        coEvery {
            movieDao.getTopRatedMovies()
        } returns MoviesEntityTopRated(
            "",
            1,
            "",
            1,
            1
        )


        val expectedValue =  MoviesEntityTopRated(
            "",
            1,
            "",
            1,
            1
        ).toMovies().right()

        val result = repositoryImpl.getRoomTopRatedMovies()

        coVerify { movieDao.getTopRatedMovies() }

        assertEquals(expectedValue, result)
    }

    @Test
    fun getRoomUpcomingMovies() = runTest {
        coEvery {
            movieDao.getUpcomingMovies()
        } returns MoviesEntityUpcoming(
            "",
            1,
            "",
            1,
            1
        )


        val expectedValue =  MoviesEntityUpcoming(
            "",
            1,
            "",
            1,
            1
        ).toMovies().right()

        val result = repositoryImpl.getRoomUpcomingMovies()

        coVerify { movieDao.getUpcomingMovies() }

        assertEquals(expectedValue, result)
    }

    @Test
    fun getErrorResult()  {
        val expectedValue =  Error(0,"", false )
        val result = error

        assertEquals(expectedValue, result)
    }

}