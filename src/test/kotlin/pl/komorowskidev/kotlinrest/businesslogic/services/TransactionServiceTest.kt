package pl.komorowskidev.kotlinrest.businesslogic.services

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.komorowskidev.kotlinrest.businesslogic.converters.TransactionListConverter
import pl.komorowskidev.kotlinrest.db.dao.TransactionDao
import pl.komorowskidev.kotlinrest.db.repository.TransactionRepository
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import pl.komorowskidev.kotlinrest.util.IdsExtractor
import java.time.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionServiceTest {

    private lateinit var service: TransactionService
    private lateinit var repositoryMock: TransactionRepository
    private lateinit var converterMock: TransactionListConverter
    private lateinit var idsExtractorMock: IdsExtractor

    @BeforeTest
    fun init(){
        repositoryMock = mock(TransactionRepository::class.java)
        converterMock = mock(TransactionListConverter::class.java)
        idsExtractorMock = mock(IdsExtractor::class.java)
        service = TransactionService(repositoryMock, converterMock, idsExtractorMock)
    }

    @Test
    fun shouldCallRepositoryAndReturnListOfTransactions(){
        //given:
        val accountTypeId = "accountTypeId1"
        val customerId = "customerId1"
        val accountTypeIdSet = setOf(1L, 2L)
        `when`(idsExtractorMock.getIdSet(accountTypeId)).thenReturn(accountTypeIdSet)
        val customerIdSet = setOf(3L, 4L)
        `when`(idsExtractorMock.getIdSet(customerId)).thenReturn(customerIdSet)
        val transactionDaoList = listOf(TransactionDao(1L, 2, 3L, 4L, Instant.now()))
        `when`(repositoryMock
                .findByCustomerIdInAndAccountTypeIdInOrderByAmountInCentsAsc(customerIdSet, accountTypeIdSet))
                .thenReturn(transactionDaoList)
        val transactionDtoList = listOf(TransactionDto("", 11L, "", "", "", ""))
        `when`(converterMock.toTransactionDto(transactionDaoList)).thenReturn(transactionDtoList)

        //when:
        val actual = service.getTransactions(accountTypeId, customerId)

        //then:
        assertEquals(transactionDtoList, actual)
    }

    @Test
    fun shouldCallRepositoryAndReturnListOfTransactionsForAllAccountTypes(){
        //given:
        val accountTypeId = "ALL"
        val customerId = "customerId1"
        val customerIdSet = setOf(3L, 4L)
        `when`(idsExtractorMock.getIdSet(customerId)).thenReturn(customerIdSet)
        val transactionDaoList = listOf(TransactionDao(1L, 2, 3L, 4L, Instant.now()))
        `when`(repositoryMock
                .findByCustomerIdInOrderByAmountInCentsAsc(customerIdSet))
                .thenReturn(transactionDaoList)
        val transactionDtoList = listOf(TransactionDto("", 11L, "", "", "", ""))
        `when`(converterMock.toTransactionDto(transactionDaoList)).thenReturn(transactionDtoList)

        //when:
        val actual = service.getTransactions(accountTypeId, customerId)

        //then:
        assertEquals(transactionDtoList, actual)
    }

    @Test
    fun shouldCallRepositoryAndReturnListOfTransactionsForAllCustomers(){
        //given:
        val accountTypeId = "accountTypeId1"
        val customerId = "ALL"
        val accountTypeIdSet = setOf(1L, 2L)
        `when`(idsExtractorMock.getIdSet(accountTypeId)).thenReturn(accountTypeIdSet)
        val transactionDaoList = listOf(TransactionDao(1L, 2, 3L, 4L, Instant.now()))
        `when`(repositoryMock
                .findByAccountTypeIdInOrderByAmountInCentsAsc(accountTypeIdSet))
                .thenReturn(transactionDaoList)
        val transactionDtoList = listOf(TransactionDto("", 11L, "", "", "", ""))
        `when`(converterMock.toTransactionDto(transactionDaoList)).thenReturn(transactionDtoList)

        //when:
        val actual = service.getTransactions(accountTypeId, customerId)

        //then:
        assertEquals(transactionDtoList, actual)
    }

    @Test
    fun shouldCallRepositoryAndReturnListOfTransactionsForAllCustomersAndAllAccountTypes(){
        //given:
        val accountTypeId = "ALL"
        val customerId = "ALL"
        val transactionDaoList = listOf(TransactionDao(1L, 2, 3L, 4L, Instant.now()))
        `when`(repositoryMock
                .findAllByOrderByAmountInCentsAsc())
                .thenReturn(transactionDaoList)
        val transactionDtoList = listOf(TransactionDto("", 11L, "", "", "", ""))
        `when`(converterMock.toTransactionDto(transactionDaoList)).thenReturn(transactionDtoList)

        //when:
        val actual = service.getTransactions(accountTypeId, customerId)

        //then:
        assertEquals(transactionDtoList, actual)
    }
}
