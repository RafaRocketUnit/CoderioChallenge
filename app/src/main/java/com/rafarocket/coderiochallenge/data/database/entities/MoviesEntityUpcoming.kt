package com.rafarocket.coderiochallenge.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.rafarocket.coderiochallenge.domain.model.Movies

@Entity (tableName = "upcoming")
data class MoviesEntityUpcoming(
    @ColumnInfo (name = "dates") val dates: String?,
    @ColumnInfo (name = "page") val page: Int,
    @ColumnInfo (name = "results") val results: String,
    @ColumnInfo (name = "total_pages") val total_pages: Int,
    @ColumnInfo (name = "total_results") val total_results: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

fun Movies.toMoviesEntityUpcoming(): MoviesEntityUpcoming {
    val result = Gson().toJson(this.results.map { it.toMovieEntity()})
    val dates: String? = if (dates == null) null else "${this.dates.maximum},${this.dates.maximum}"
    return MoviesEntityUpcoming(dates = dates, page = this.page, results = result, total_pages = this.total_pages, total_results = this.total_results)
}


