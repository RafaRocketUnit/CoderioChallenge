package com.rafarocket.coderiochallenge.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.rafarocket.coderiochallenge.domain.model.PopularPerson

@Entity(tableName = "popularperson_table")
data class PopularPersonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "results") val results: String?,
    @ColumnInfo(name = "total_pages") val total_pages: Int,
    @ColumnInfo(name = "total_results") val total_results: Int
)

fun PopularPerson.toPopularPersonEntity(): PopularPersonEntity {
    val result = Gson().toJson(this.results.map { it.toPersonEntity()})
    return PopularPersonEntity(page = page, results = result, total_pages = total_pages, total_results = total_results)
}