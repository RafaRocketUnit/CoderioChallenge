package com.rafarocket.coderiochallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafarocket.coderiochallenge.data.database.dao.MoviesDao
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityPopular
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityTopRated
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityUpcoming

@Database(entities = [MoviesEntityPopular::class, MoviesEntityTopRated::class, MoviesEntityUpcoming::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}