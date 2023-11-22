package com.rafarocket.coderiochallenge.data.poc

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.rafarocket.coderiochallenge.data.pom.AppUiModel

class AppUiController {

    private val uiModel = AppUiModel()

    fun getPersonTab() {
        uiModel.apply {
            getButtonProfile().perform(click())
            getPersonTab().apply {
                check(matches(isDisplayed()))
                perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
            }
        }
    }

    fun getMoviesTab() {
        uiModel.apply {
            getButtonMovies().perform(click())

            getMoviesPopular().perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
            getMoviesTopRated().perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
            getMoviesUpcoming().perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))

            getMoviesTab().check(matches(isDisplayed()))
        }
    }

    fun getMapTab() {
        uiModel.apply {
            getButtonMap().perform(click())
            getMapTab().check(matches(isDisplayed()))
        }
    }

    fun getPictureTab() {
        uiModel.apply {
            getButtonPictures().perform(click())
            getPictureTab().check(matches(isDisplayed()))
        }
    }
}