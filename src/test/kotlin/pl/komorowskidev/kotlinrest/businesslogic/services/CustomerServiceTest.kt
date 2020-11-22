package pl.komorowskidev.kotlinrest.businesslogic.services

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.komorowskidev.kotlinrest.db.dao.CustomerDao
import pl.komorowskidev.kotlinrest.db.repository.CustomerRepository
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
    fun shouldCallRepositoryAndReturnMapOfCustomers(){
        //given:
        val customerIdSet = setOf(1L, 2L)
        val id1 = 1L
        val id2 = 2L
        val customer1 = CustomerDao(id1, "first", "last", 10)
        val customer2 = CustomerDao(id2, "first2", "last2", 20)
        val customerList = listOf(customer1, customer2)
        `when`(repositoryMock.findAllById(customerIdSet)).thenReturn(customerList)

        //when:
        val actual = service.getCustomerMap(customerIdSet)

        //then:
        assertEquals(2, actual.size)
        assertEquals(customer1, actual[id1])
        assertEquals(customer2, actual[id2])
    }
}
