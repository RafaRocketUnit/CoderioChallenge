package com.rafarocket.coderiochallenge.domain.usecases

import arrow.core.Either
import arrow.core.right
import com.rafarocket.coderiochallenge.data.PersonRepository
import com.rafarocket.coderiochallenge.data.database.entities.PopularPersonEntity
import com.rafarocket.coderiochallenge.data.database.entities.toPopularPersonEntity
import com.rafarocket.coderiochallenge.domain.model.Error
import com.rafarocket.coderiochallenge.domain.model.PopularPerson
import com.rafarocket.coderiochallenge.domain.model.toPopularPerson
import javax.inject.Inject

class PopularPersonUseCase @Inject constructor(private val personRepository: PersonRepository) {

    suspend operator fun invoke(): Either<Error, PopularPerson> {
        val popularPerson = personRepository.getApiPopularPerson()

        return if (popularPerson.isRight()) {
            personRepository.clearPopularPerson(
                popularPerson.orNull()?.toPopularPersonEntity() as PopularPersonEntity
            )
            personRepository.insertPopularPerson(
                popularPerson.orNull()?.toPopularPersonEntity() as PopularPersonEntity
            )
            popularPerson
        } else {
            personRepository.getRoomPopularPerson()
        }
    }

}