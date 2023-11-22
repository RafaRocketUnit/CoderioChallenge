package com.rafarocket.coderiochallenge.domain.model

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityPopular
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityTopRated
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityUpcoming

data class Movies(
    val dates: Dates?,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

fun MoviesEntityPopular.toMovies(): Movies {
    val result = transformFromStringToList(results)
    val dates = this.dates?.split(",")
    return Movies(
        if (dates?.size as Int > 1)
            Dates(dates[0] , dates[1])
                    else
            Dates("", "")
        ,
        this.page,
        result,
        this.total_pages,
        this.total_results
    )
}

fun MoviesEntityTopRated.toMovies(): Movies {
    val result = transformFromStringToList(results)
    val dates = this.dates?.split(",")
    return Movies(
        if (dates?.size as Int > 1)
            Dates(dates[0] , dates[1])
        else
            Dates("", "")
        ,
        this.page,
        result,
        this.total_pages,
        this.total_results
    )
}

fun MoviesEntityUpcoming.toMovies(): Movies {
    val result = transformFromStringToList(results)
    val dates = this.dates?.split(",")
    return Movies(
        if (dates?.size as Int > 1)
            Dates(dates[0] , dates[1])
        else
            Dates("", "")
        ,
        this.page,
        result,
        this.total_pages,
        this.total_results
    )
}

private fun transformFromStringToList(result: String): List<Movie> {
    val gson = Gson()
    val trade = if (result.isNullOrEmpty())
        return listOf()
    else
        JsonParser().parse(result).asJsonArray

    val list = mutableListOf<Movie>()
    for (item in trade) {
        list.add(gson.fromJson(gson.toJson(item),Movie::class.java))
    }
    return list
}