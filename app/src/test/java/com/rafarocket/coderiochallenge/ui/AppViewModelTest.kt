package com.rafarocket.coderiochallenge.ui

import app.cash.turbine.test
import arrow.core.right
import com.rafarocket.coderiochallenge.domain.model.Dates
import com.rafarocket.coderiochallenge.domain.model.Movie
import com.rafarocket.coderiochallenge.domain.model.Movies
import com.rafarocket.coderiochallenge.domain.model.PopularPerson
import com.rafarocket.coderiochallenge.domain.usecases.PopularMoviesUseCase
import com.rafarocket.coderiochallenge.domain.usecases.PopularPersonUseCase
import com.rafarocket.coderiochallenge.domain.usecases.TopRatedMoviesUseCase
import com.rafarocket.coderiochallenge.domain.usecases.UpComingMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class AppViewModelTest {

    private val useCaseUpcoming: UpComingMoviesUseCase = mockk(relaxed = true)
    private val useCaseTopRated: TopRatedMoviesUseCase = mockk(relaxed = true)
    private val useCasePopularMovies: PopularMoviesUseCase = mockk(relaxed = true)
    private val useCasePopularPerson: PopularPersonUseCase = mockk(relaxed = true)
    private val viewModel: AppViewModel = AppViewModel(
        popularMoviesUseCase = useCasePopularMovies,
        popularPersonUseCase = useCasePopularPerson,
        topRatedMoviesUseCase = useCaseTopRated,
        upComingMoviesUseCase = useCaseUpcoming
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchPopularPerson() = runTest {
        coEvery {
            useCasePopularPerson()
        } returns PopularPerson(
            1,
            listOf(),
            1,
            1
        ).right()

        val expectedValue = ViewStates.PopularPersonResult(PopularPerson(
            1,
            listOf(),
            1,
            1
        ))

        viewModel.fetchPopularPerson()
        coVerify { useCasePopularPerson() }

        viewModel.useCases.test {
            assertEquals(expectedValue, expectMostRecentItem())
        }
    }

    @Test
    fun fetchPopularMovies() = runTest {
        coEvery {
            useCasePopularMovies()
        } returns Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ).right()

        val expectedValue = ViewStates.PopularMoviesResult(Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ))

        viewModel.fetchPopularMovies()
        coVerify { useCasePopularMovies() }

        viewModel.useCases.test {
            assertEquals(expectedValue, expectMostRecentItem())
        }
    }

    @Test
    fun fetchTopRatedMovies() = runTest {
        coEvery {
            useCaseTopRated()
        } returns Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ).right()

        val expectedValue = ViewStates.TopRatedResult(Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ))

        viewModel.fetchTopRatedMovies()
        coVerify { useCaseTopRated() }

        viewModel.useCases.test {
            assertEquals(expectedValue, expectMostRecentItem())
        }
    }

    @Test
    fun fetchUpComingMovies() = runTest {
        coEvery {
            useCaseUpcoming()
        } returns Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ).right()

        val expectedValue = ViewStates.UpComingResult(Movies(
            Dates("", ""),
            1,
            listOf<Movie>(),
            1,
            1
        ))

        viewModel.fetchUpComingMovies()
        coVerify { useCaseUpcoming() }

        viewModel.useCases.test {
            assertEquals(expectedValue, expectMostRecentItem())
        }
    }
}