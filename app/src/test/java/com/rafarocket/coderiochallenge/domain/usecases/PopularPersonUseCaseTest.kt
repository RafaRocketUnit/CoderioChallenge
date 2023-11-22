package com.rafarocket.coderiochallenge.domain.usecases

import arrow.core.right
import com.rafarocket.coderiochallenge.data.PersonRepository
import com.rafarocket.coderiochallenge.domain.model.PopularPerson
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PopularPersonUseCaseTest {

    private val repository: PersonRepository = mockk(relaxed = true)
    private val useCase: PopularPersonUseCase = PopularPersonUseCase(repository)

    @Test
    fun invoke() = runTest {

        coEvery {
            repository.getApiPopularPerson()
        } returns PopularPerson(
            1,
            listOf(),
            1,
            1
        ).right()

        val expectedValue = PopularPerson(
            1,
            listOf(),
            1,
            1
        ).right()

        val result = useCase.invoke()

        coVerify { repository.getApiPopularPerson() }

        TestCase.assertEquals(expectedValue, result)
    }

    @Test
    fun invokeRoom() = runTest {
        coEvery {
            repository.getRoomPopularPerson()
        } returns PopularPerson(
            1,
            listOf(),
            1,
            1
        ).right()

        val expectedValue = PopularPerson(
            1,
            listOf(),
            1,
            1
        ).right()

        val result = useCase.invoke()

        coVerify { repository.getRoomPopularPerson() }

        TestCase.assertEquals(expectedValue, result)
    }
}