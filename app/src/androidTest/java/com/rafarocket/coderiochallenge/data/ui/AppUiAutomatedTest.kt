package com.rafarocket.coderiochallenge.data.ui

import androidx.test.core.app.ActivityScenario
import com.rafarocket.coderiochallenge.MainActivity
import com.rafarocket.coderiochallenge.data.poc.AppUiController
import org.junit.Before
import org.junit.Test

class AppUiAutomatedTest {

    private val controller = AppUiController()

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun checkProfile() {
        controller.getPersonTab()
    }

    @Test
    fun checkMovies() {
        controller.getMoviesTab()
    }
    @Test
    fun checkMap() {
        controller.getMapTab()
    }

    @Test
    fun checkPictures() {
        controller.getPictureTab()
    }
}