package com.rafarocket.coderiochallenge.domain.usecases

import arrow.core.right
import com.rafarocket.coderiochallenge.data.MoviesRepository
import com.rafarocket.coderiochallenge.domain.model.Dates
import com.rafarocket.coderiochallenge.domain.model.Movie
import com.rafarocket.coderiochallenge.domain.model.Movies
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpComingMoviesUseCaseTest {

    private val repository: MoviesRepository = mockk(relaxed = true)
    private val useCase: UpComingMoviesUseCase = UpComingMoviesUseCase(repository)

    @Test
    fun invoke() = runTest {

        coEvery {
            repository.getApiUpcomingMovies()
        } returns Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ).right()

        val expectedValue = Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ).right()

        val result = useCase.invoke()

        coVerify { repository.getApiUpcomingMovies() }

        assertEquals(expectedValue, result)
    }

    @Test
    fun invokeRoom() = runTest {
        coEvery {
            repository.getRoomUpcomingMovies()
        } returns Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ).right()

        val expectedValue = Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ).right()
        val result = useCase.invoke()

        coVerify { repository.getRoomUpcomingMovies() }

        assertEquals(expectedValue, result)
    }

}