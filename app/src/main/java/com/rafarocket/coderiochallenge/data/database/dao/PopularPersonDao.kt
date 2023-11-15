package com.rafarocket.coderiochallenge.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rafarocket.coderiochallenge.data.database.entities.PopularPersonEntity

@Dao
interface PopularPersonDao {

    @Insert
    fun insert(popularPerson: PopularPersonEntity)

    @Update
    fun update(popularPerson: PopularPersonEntity)

    @Delete
    fun delete(popularPerson: PopularPersonEntity)

    @Query("select * from popularperson_table")
    fun getPopularPerson(): PopularPersonEntity
}