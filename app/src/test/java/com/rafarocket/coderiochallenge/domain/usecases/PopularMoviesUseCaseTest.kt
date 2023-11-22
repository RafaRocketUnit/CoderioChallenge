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

class PopularMoviesUseCaseTest {

    private val repository: MoviesRepository = mockk(relaxed = true)
    private val useCase: PopularMoviesUseCase = PopularMoviesUseCase(repository)

    @Test
    fun invoke() = runTest {

        coEvery {
            repository.getApiPopularMovies()
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

        coVerify { repository.getApiPopularMovies() }

        assertEquals(expectedValue, result)
    }

    @Test
    fun invokeRoom() = runTest {
        coEvery {
            repository.getRoomPopularMovies()
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

        coVerify { repository.getRoomPopularMovies() }

        assertEquals(expectedValue, result)
    }

}