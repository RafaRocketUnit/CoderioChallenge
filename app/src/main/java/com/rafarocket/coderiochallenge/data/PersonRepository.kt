package com.rafarocket.coderiochallenge.data

import arrow.core.Either
import com.rafarocket.coderiochallenge.data.database.entities.PopularPersonEntity
import com.rafarocket.coderiochallenge.domain.model.Error
import com.rafarocket.coderiochallenge.domain.model.PopularPerson

interface PersonRepository {

    suspend fun getApiPopularPerson(): Either<Error, PopularPerson>
    suspend fun getRoomPopularPerson(): Either<Error, PopularPerson>
    suspend fun insertPopularPerson(popularPerson: PopularPersonEntity)
    suspend fun clearPopularPerson(popularPerson: PopularPersonEntity)

}