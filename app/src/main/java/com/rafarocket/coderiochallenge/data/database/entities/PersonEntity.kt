package com.rafarocket.coderiochallenge.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.rafarocket.coderiochallenge.domain.model.Person

@Entity(tableName = "person_table")
data class PersonEntity(
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "gender") val gender: Int,
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "known_for") val known_for: String?,
    @ColumnInfo(name = "known_for_department") val known_for_department: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "original_name") val original_name: String?,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "profile_path") val profile_path: String?
)

fun Person.toPersonEntity(): PersonEntity {
    val knownFor = Gson().toJson(this.known_for.map { it.toKnownForEntity() })
    return PersonEntity(
        adult,
        gender,
        id,
        knownFor,
        known_for_department,
        name,
        original_name,
        popularity,
        profile_path
    )
}