package com.rafarocket.coderiochallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafarocket.coderiochallenge.domain.usecases.PopularMoviesUseCase
import com.rafarocket.coderiochallenge.domain.usecases.PopularPersonUseCase
import com.rafarocket.coderiochallenge.domain.usecases.TopRatedMoviesUseCase
import com.rafarocket.coderiochallenge.domain.usecases.UpComingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val popularPersonUseCase: PopularPersonUseCase,
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val topRatedMoviesUseCase: TopRatedMoviesUseCase,
    private val upComingMoviesUseCase: UpComingMoviesUseCase
    ): ViewModel() {

    private val _useCases = MutableStateFlow<ViewStates>(ViewStates.Idle)
    val useCases: Flow<ViewStates> = _useCases

    fun fetchPopularPerson() {
        viewModelScope.launch {
            val result = popularPersonUseCase.invoke()
            _useCases.value = result.fold(
                ifLeft = {
                    ViewStates.ErrorUi(it)
                },
                ifRight = {
                    ViewStates.PopularPersonResult(it)
                }
            )
        }
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            val result = popularMoviesUseCase.invoke()
            _useCases.value = result.fold(
                ifLeft = {
                    ViewStates.ErrorUi(it)
                },
                ifRight = {
                    ViewStates.PopularMoviesResult(it)
                }
            )
        }
    }

    fun fetchTopRatedMovies() {
        viewModelScope.launch {
            val result = topRatedMoviesUseCase.invoke()
            _useCases.value = result.fold(
                ifLeft = {
                    ViewStates.ErrorUi(it)
                },
                ifRight = {
                    ViewStates.TopRatedResult(it)
                }
            )
        }
    }

    fun fetchUpComingMovies() {
        viewModelScope.launch {
            val result = upComingMoviesUseCase.invoke()
            _useCases.value = result.fold(
                ifLeft = {
                    ViewStates.ErrorUi(it)
                },
                ifRight = {
                    ViewStates.UpComingResult(it)
                }
            )
        }
    }

}