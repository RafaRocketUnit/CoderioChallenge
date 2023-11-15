package com.rafarocket.coderiochallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafarocket.coderiochallenge.data.database.dao.PopularPersonDao
import com.rafarocket.coderiochallenge.data.database.entities.PopularPersonEntity

@Database(entities = [PopularPersonEntity::class], version = 1)
abstract class PersonsDatabase: RoomDatabase() {
    abstract fun personsDao(): PopularPersonDao
}