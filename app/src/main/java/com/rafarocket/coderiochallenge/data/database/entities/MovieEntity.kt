package com.rafarocket.coderiochallenge.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rafarocket.coderiochallenge.domain.model.Movie

@Entity(tableName = "movie_table")
data class MovieEntity(
    @ColumnInfo(name = "adult") val adult: Boolean = false,
    @ColumnInfo(name = "backdrop_path") val backdrop_path: String = "",
    @ColumnInfo(name = "genre_ids") val genre_ids: List<Int> = listOf(),
    @PrimaryKey(autoGenerate = false) val id: Int = -1,
    @ColumnInfo(name = "original_language") val original_language: String = "",
    @ColumnInfo(name = "original_title") val original_title: String = "",
    @ColumnInfo(name = "overview") val overview: String = "",
    @ColumnInfo(name = "popularity") val popularity: Double = 0.0,
    @ColumnInfo(name = "poster_path") val poster_path: String = "",
    @ColumnInfo(name = "release_date") val release_date: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "video") val video: Boolean = false,
    @ColumnInfo(name = "vote_average") val vote_average: Double = 0.0,
    @ColumnInfo(name = "vote_count") val vote_count: Int = -1
)

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        adult,
        backdrop_path,
        genre_ids,
        id,
        original_language,
        original_title,
        overview,
        popularity,
        poster_path,
        release_date,
        title,
        video,
        vote_average,
        vote_count
    )
}