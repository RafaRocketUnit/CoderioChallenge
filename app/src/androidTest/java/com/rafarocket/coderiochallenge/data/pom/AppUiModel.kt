package com.rafarocket.coderiochallenge.data.pom

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.rafarocket.coderiochallenge.R

class AppUiModel {

    fun getPersonTab(): ViewInteraction = onView(withId(R.id.recyclerview))
    fun getMoviesTab(): ViewInteraction = onView(withId(R.id.movies_container))
    fun getMoviesPopular(): ViewInteraction = onView(withId(R.id.popularRecycler))
    fun getMoviesTopRated(): ViewInteraction = onView(withId(R.id.topRatedRecycler))
    fun getMoviesUpcoming(): ViewInteraction = onView(withId(R.id.upComingRecycler))
    fun getMapTab(): ViewInteraction = onView(withId(R.id.mapView))
    fun getPictureTab(): ViewInteraction = onView(withId(R.id.titleUpload))

    fun getButtonProfile(): ViewInteraction = onView(withId(R.id.navigation_profile))
    fun getButtonMovies(): ViewInteraction = onView(withId(R.id.navigation_movies))
    fun getButtonMap(): ViewInteraction = onView(withId(R.id.navigation_map))
    fun getButtonPictures(): ViewInteraction = onView(withId(R.id.navigation_load_image))

}