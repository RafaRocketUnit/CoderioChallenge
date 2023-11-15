package com.rafarocket.coderiochallenge.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.rafarocket.coderiochallenge.domain.model.Dates

@Entity(tableName = "dates_table")
data class DatesEntity(
    @ColumnInfo (name = "maximum") val maximum: String?,
    @ColumnInfo(name = "minimum") val minimum: String?
    )
