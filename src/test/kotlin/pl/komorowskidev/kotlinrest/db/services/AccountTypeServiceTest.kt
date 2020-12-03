package pl.komorowskidev.kotlinrest.db.services

import org.mockito.Mockito.*
import pl.komorowskidev.kotlinrest.db.entities.AccountTypeEntity
import pl.komorowskidev.kotlinrest.db.repository.AccountTypeRepository
import java.util.*
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
    fun shouldSendEntityToRepository(){
        //given:
        val accountType = AccountTypeEntity(1L, "one")

        //when:
        service.save(accountType)

        //then:
        verify(repositoryMock).save(accountType)
    }

    @Test
    fun shouldCallRepositoryAndReturnOptionalOfAccountTypes(){
        //given:
        val id = 1L
        val accountType = AccountTypeEntity(id, "one")
        val expected = Optional.of(accountType)
        `when`(repositoryMock.findById(id)).thenReturn(expected)

        //when:
        val actual = service.findById(id)

        //then:
        assertEquals(expected, actual)
    }
}
