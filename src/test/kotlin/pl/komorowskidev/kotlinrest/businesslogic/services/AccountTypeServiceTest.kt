package pl.komorowskidev.kotlinrest.businesslogic.services

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.komorowskidev.kotlinrest.db.dao.AccountTypeDao
import pl.komorowskidev.kotlinrest.db.repository.AccountTypeRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountTypeServiceTest {

    private lateinit var service: AccountTypeService
    private lateinit var repositoryMock: AccountTypeRepository

    @BeforeTest
    fun init(){
        repositoryMock = mock(AccountTypeRepository::class.java)
        service = AccountTypeService(repositoryMock)
    }

    @Test
    fun shouldCallRepositoryAndReturnMapOfAccountTypes(){
        //given:
        val accountTypeIdSet = setOf(1L, 2L)
        val id1 = 1L
        val id2 = 2L
        val accountType1 = AccountTypeDao(id1, "one")
        val accountType2 = AccountTypeDao(id2, "two")
        val accountTypeList = listOf(accountType1, accountType2)
        `when`(repositoryMock.findAllById(accountTypeIdSet)).thenReturn(accountTypeList)

        //when:
        val actual = service.getAccountTypeMap(accountTypeIdSet)

        //then:
        assertEquals(2, actual.size)
        assertEquals(accountType1, actual[id1])
        assertEquals(accountType2, actual[id2])
    }
}
