package pl.komorowskidev.kotlinrest.db.services

import org.mockito.Mockito.*
import pl.komorowskidev.kotlinrest.db.entities.TransactionEntity
import pl.komorowskidev.kotlinrest.db.repository.TransactionRepository
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import pl.komorowskidev.kotlinrest.util.IdsExtractor
import pl.komorowskidev.kotlinrest.util.converters.TransactionConverter
import java.time.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TransactionServiceTest {

    private lateinit var service: TransactionService
    private lateinit var repositoryMock: TransactionRepository
    private lateinit var idsExtractorMock: IdsExtractor
    private lateinit var transactionConverterMock: TransactionConverter

    @BeforeTest
    fun init(){
        repositoryMock = mock(TransactionRepository::class.java)
        idsExtractorMock = mock(IdsExtractor::class.java)
        transactionConverterMock = mock(TransactionConverter::class.java)
        service = TransactionService(repositoryMock, idsExtractorMock, transactionConverterMock)
    }

    @Test
    fun shouldSendEntityToRepository(){
        //given:
        val transactionEntity = TransactionEntity(
                1L,
                100,
                2L,
                "type",
                3L,
                "first",
                "last",
                200,
                Instant.now())

        //when:
        service.save(transactionEntity)

        //then:
        verify(repositoryMock).save(transactionEntity)
    }

    @Test
    fun shouldCallRepositoryAllTransactions() {
        //given:
        val accountTypeId = "ALL"
        val customerId = "ALL"

        //when:
        service.getTransactionsDto(accountTypeId, customerId)

        //then:
        verify(repositoryMock).findAllByOrderByAmountInCentsAsc()
    }

    @Test
    fun shouldCallRepositoryTransactionOfAllAccountType() {
        //given:
        val accountTypeId = "ALL"
        val customerId = ""
        val customerIdSet = setOf(3L, 4L)
        `when`(idsExtractorMock.getIdSet(customerId)).thenReturn(customerIdSet)

        //when:
        service.getTransactionsDto(accountTypeId, customerId)

        //then:
        verify(repositoryMock).findByCustomerIdInOrderByAmountInCentsAsc(customerIdSet)
    }

    @Test
    fun shouldCallRepositoryTransactionOfAllCustomers() {
        //given:
        val accountTypeId = ""
        val customerId = "ALL"
        val accountTypeIdSet = setOf(3L, 4L)
        `when`(idsExtractorMock.getIdSet(accountTypeId)).thenReturn(accountTypeIdSet)

        //when:
        service.getTransactionsDto(accountTypeId, customerId)

        //then:
        verify(repositoryMock).findByAccountTypeIdInOrderByAmountInCentsAsc(accountTypeIdSet)
    }

    @Test
    fun shouldCallRepositoryTransaction() {
        //given:
        val accountTypeId = "a"
        val customerId = "c"
        val customerIdSet = setOf(1L, 2L)
        `when`(idsExtractorMock.getIdSet(customerId)).thenReturn(customerIdSet)
        val accountTypeIdSet = setOf(3L, 4L)
        `when`(idsExtractorMock.getIdSet(accountTypeId)).thenReturn(accountTypeIdSet)

        //when:
        service.getTransactionsDto(accountTypeId, customerId)

        //then:
        verify(repositoryMock).findByCustomerIdInAndAccountTypeIdInOrderByAmountInCentsAsc(
                customerIdSet,
                accountTypeIdSet)
    }

    @Test
    fun shouldReturnListOfTransactionDto(){
        //given:
        val accountTypeId = "ALL"
        val customerId = "All"
        val transactionEntity1 = TransactionEntity(
                1L,
                200,
                3L,
                "type",
                4L,
                "first",
                "last",
                2,
                Instant.now())
        val transactionEntity2 = TransactionEntity(
                2L,
                202,
                4L,
                "tttype",
                5L,
                "first2",
                "last2",
                20,
                Instant.now())
        val transactionEntityList = listOf(transactionEntity1, transactionEntity2)
        `when`(repositoryMock.findAllByOrderByAmountInCentsAsc()).thenReturn(transactionEntityList)
        val transactionDto1 = TransactionDto(
                "",
                11L,
                "",
                "",
                "",
                "")
        val transactionDto2 = TransactionDto(
                "",
                22L,
                "",
                "",
                "",
                "")
        `when`(transactionConverterMock.entityToDto(transactionEntity1)).thenReturn(transactionDto1)
        `when`(transactionConverterMock.entityToDto(transactionEntity2)).thenReturn(transactionDto2)

        //when:
        val actual = service.getTransactionsDto(accountTypeId, customerId)

        //then:
        assertEquals(2, actual.size)
        assertTrue(actual.contains(transactionDto1))
        assertTrue(actual.contains(transactionDto2))
    }

}
