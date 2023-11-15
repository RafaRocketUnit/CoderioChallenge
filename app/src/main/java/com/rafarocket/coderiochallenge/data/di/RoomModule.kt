package com.rafarocket.coderiochallenge.data.di

import android.content.Context
import androidx.room.Room
import com.rafarocket.coderiochallenge.data.database.MoviesDatabase
import com.rafarocket.coderiochallenge.data.database.PersonsDatabase
import com.rafarocket.coderiochallenge.data.database.dao.PopularPersonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class )
object RoomModule {

    private const val MOVIE_DATABASE_NAME = "MovieDatabase"
    private const val PERSON_DATABASE_NAME = "PersonDatabase"

    @Singleton
    @Provides
    fun provideRoomMovie(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            MOVIE_DATABASE_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMoviesDao(db:MoviesDatabase) = db.moviesDao()

    @Singleton
    @Provides
    fun provideRoomPerson(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PersonsDatabase::class.java, PERSON_DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun providePopularPerson(db:PersonsDatabase): PopularPersonDao {
        return db.personsDao()
    }

}