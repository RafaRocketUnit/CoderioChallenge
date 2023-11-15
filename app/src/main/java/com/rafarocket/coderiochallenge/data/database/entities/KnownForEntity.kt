package com.rafarocket.coderiochallenge.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.rafarocket.coderiochallenge.domain.model.KnownFor

@Entity(tableName = "known_table")
data class KnownForEntity(
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") val backdrop_path: String?,
    @ColumnInfo(name = "first_air_date") val first_air_date: String?,
    @ColumnInfo(name = "genre_ids") val genre_ids: String?,
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "media_type") val media_type: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "origin_country") val origin_country: String?,
    @ColumnInfo(name = "original_language") val original_language: String?,
    @ColumnInfo(name = "original_name") val original_name: String?,
    @ColumnInfo(name = "original_title") val original_title: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val poster_path: String?,
    @ColumnInfo(name = "release_date") val release_date: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "video") val video: Boolean,
    @ColumnInfo(name = "vote_average") val vote_average: Double,
    @ColumnInfo(name = "vote_count") val vote_count: Int
)

fun KnownFor.toKnownForEntity(): KnownForEntity {

    val genre = Gson().toJson(this.genre_ids)
    val originCountry = Gson().toJson(this.origin_country)
    return KnownForEntity(
        adult,
        backdrop_path,
        first_air_date,
        genre,
        id,
        media_type,
        name,
        originCountry,
        original_language,
        original_name,
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