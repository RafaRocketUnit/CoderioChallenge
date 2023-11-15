package com.rafarocket.coderiochallenge.ui

import com.rafarocket.coderiochallenge.domain.model.Error
import com.rafarocket.coderiochallenge.domain.model.PopularPerson
import com.rafarocket.coderiochallenge.domain.model.Movies

sealed class ViewStates {
    data class PopularPersonResult(val popularPerson: PopularPerson): ViewStates()
    data class PopularMoviesResult(val movies: Movies): ViewStates()
    data class TopRatedResult(val movies: Movies): ViewStates()
    data class UpComingResult(val movies: Movies): ViewStates()

    data class ErrorUi(val error: Error): ViewStates()
    object Idle: ViewStates()
}