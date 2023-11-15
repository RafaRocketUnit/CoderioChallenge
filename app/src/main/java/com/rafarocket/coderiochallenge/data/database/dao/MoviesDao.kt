package com.rafarocket.coderiochallenge.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityPopular
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityTopRated
import com.rafarocket.coderiochallenge.data.database.entities.MoviesEntityUpcoming

@Dao
interface MoviesDao {

    // TopRated
    @Insert
    suspend fun insertTopRated(moviesData: MoviesEntityTopRated)

    @Update
    suspend fun updateTopRated(moviesData: MoviesEntityTopRated)

    @Delete
    suspend fun deleteTopRated(moviesData: MoviesEntityTopRated)

    @Query("select * from top_rated")
    suspend fun getTopRatedMovies(): MoviesEntityTopRated


    // PopularMovies

    @Insert
    suspend fun insertPopularMovies(moviesData: MoviesEntityPopular)

    @Update
    suspend fun updatePopularMovies(moviesData: MoviesEntityPopular)

    @Delete
    suspend fun deletePopularMovies(moviesData: MoviesEntityPopular)

    @Query("select * from popular")
    suspend fun getPopularMovies(): MoviesEntityPopular

    // Upcoming

    @Insert
    suspend fun insertUpcoming(moviesData: MoviesEntityUpcoming)

    @Update
    suspend fun updateUpcoming(moviesData: MoviesEntityUpcoming)

    @Delete
    suspend fun deleteUpcoming(moviesData: MoviesEntityUpcoming)

    @Query("select * from upcoming")
    suspend fun getUpcomingMovies(): MoviesEntityUpcoming

}