package com.rafarocket.coderiochallenge.data


import arrow.core.right
import com.rafarocket.coderiochallenge.data.database.dao.PopularPersonDao
import com.rafarocket.coderiochallenge.data.database.entities.PopularPersonEntity
import com.rafarocket.coderiochallenge.domain.model.Error
import com.rafarocket.coderiochallenge.domain.model.PopularPerson
import com.rafarocket.coderiochallenge.domain.model.toPopularPerson
import com.rafarocket.coderiochallenge.tokenAuthBearerTMDB
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PersonRepositoryImplTest {

    private val serviceApi: ApiService = mockk(relaxed = true)
    private val personDao: PopularPersonDao = mockk(relaxed = true)
    private val repositoryImpl = PersonRepositoryImpl(serviceApi, personDao)
    private val error = Error(0,"", false )
    private val headers = mutableMapOf<String, String>()

    @Test
    fun getApiPopularPerson() = runTest {
        headers["Authorization"] = tokenAuthBearerTMDB
        coEvery {
            serviceApi.getPopularPerson(headers)
        } returns PopularPerson(
            1,
            listOf(),
            1,
            1
        )


        val expectedValue =  PopularPerson(
            1,
            listOf(),
            1,
            1
        ).right()

        val result = repositoryImpl.getApiPopularPerson()

        coVerify { serviceApi.getPopularPerson(headers) }

        assertEquals(expectedValue, result)
    }

    @Test
    fun getRoomPopularPerson() = runTest {
        coEvery {
            personDao.getPopularPerson()
        } returns PopularPersonEntity(
            1,
            1,
            "",
            1,
            1
        )


        val expectedValue =  PopularPersonEntity(
            1,
            1,
            "",
            1,
            1
        ).toPopularPerson().right()

        val result = repositoryImpl.getRoomPopularPerson()

        coVerify { personDao.getPopularPerson() }

        assertEquals(expectedValue, result)
    }

    @Test
    fun getErrorResult()  {
        val expectedValue =  Error(0,"", false )
        val result = error

        assertEquals(expectedValue, result)
    }
}