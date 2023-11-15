package com.rafarocket.coderiochallenge.data

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.rafarocket.coderiochallenge.tokenAuthBearerTMDB
import com.rafarocket.coderiochallenge.data.database.dao.PopularPersonDao
import com.rafarocket.coderiochallenge.data.database.entities.PopularPersonEntity
import com.rafarocket.coderiochallenge.domain.model.Error
import com.rafarocket.coderiochallenge.domain.model.PopularPerson
import com.rafarocket.coderiochallenge.domain.model.toPopularPerson
import retrofit2.HttpException
import java.lang.IllegalStateException
import java.net.UnknownHostException
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val personDao: PopularPersonDao
    ): PersonRepository {

    override suspend fun getApiPopularPerson(): Either<Error, PopularPerson> {
        val headers = mutableMapOf<String, String>()
        headers["Authorization"] = tokenAuthBearerTMDB

        return try {
            val result = apiService.getPopularPerson(headers)
            result.right()
        } catch (exp: HttpException) {
            Error(exp.code(), exp.message as String, false).left()
        } catch (exception: UnknownHostException) {
            Error(exception.hashCode(), exception.message as String, false).left()
        }
    }

    override suspend fun getRoomPopularPerson(): Either<Error, PopularPerson> {
        return try {
             personDao.getPopularPerson().toPopularPerson().right()
        } catch (exception: IllegalStateException) {
            Error(exception.hashCode(), exception.message as String, false).left()
        }
    }

    override suspend fun insertPopularPerson(popularPerson: PopularPersonEntity) {
        try {
            personDao.insert(popularPerson)
        } catch (exception: IllegalStateException) {
            exception.printStackTrace()
        }
    }

    override suspend fun clearPopularPerson(popularPerson: PopularPersonEntity) {
        try {
            personDao.delete(popularPerson)
        } catch (exception: IllegalStateException) {
            exception.printStackTrace()
        }
    }

}