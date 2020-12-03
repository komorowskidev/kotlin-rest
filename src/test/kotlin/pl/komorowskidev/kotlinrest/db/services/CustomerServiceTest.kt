package pl.komorowskidev.kotlinrest.db.services

import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.komorowskidev.kotlinrest.db.entities.CustomerEntity
import pl.komorowskidev.kotlinrest.db.repository.CustomerRepository
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomerServiceTest {

    private lateinit var service: CustomerService
    private lateinit var repositoryMock: CustomerRepository

    @BeforeTest
    fun init(){
        repositoryMock = mock(CustomerRepository::class.java)
        service = CustomerService(repositoryMock)
    }

    @Test
    fun shouldSendEntityToRepository(){
        //given:
        val customerEntity = CustomerEntity(1L, "first", "last", 2)

        //when:
        service.save(customerEntity)

        //then:
        Mockito.verify(repositoryMock).save(customerEntity)
    }

    @Test
    fun shouldCallRepositoryAndReturnOptionalOfAccountTypes(){
        //given:
        val id = 1L
        val customerEntity = CustomerEntity(id, "first", "last", 2)
        val expected = Optional.of(customerEntity)
        `when`(repositoryMock.findById(id)).thenReturn(expected)

        //when:
        val actual = service.findById(id)

        //then:
        assertEquals(expected, actual)
    }
}
