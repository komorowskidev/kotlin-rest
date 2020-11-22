package pl.komorowskidev.kotlinrest.businesslogic.converters

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.komorowskidev.kotlinrest.beans.IdSets
import pl.komorowskidev.kotlinrest.businesslogic.services.AccountTypeService
import pl.komorowskidev.kotlinrest.businesslogic.services.CustomerService
import pl.komorowskidev.kotlinrest.db.dao.AccountTypeDao
import pl.komorowskidev.kotlinrest.db.dao.CustomerDao
import pl.komorowskidev.kotlinrest.db.dao.TransactionDao
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import pl.komorowskidev.kotlinrest.util.IdsExtractor
import java.time.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TransactionListConverterTest {

    private lateinit var converter: TransactionListConverter
    private lateinit var accountTypeServiceMock: AccountTypeService
    private lateinit var customerServiceMock: CustomerService
    private lateinit var idsExtractorMock: IdsExtractor
    private lateinit var transactionConverterMock: TransactionConverter

    @BeforeTest
    fun init(){
        accountTypeServiceMock = mock(AccountTypeService::class.java)
        customerServiceMock = mock(CustomerService::class.java)
        idsExtractorMock = mock(IdsExtractor::class.java)
        transactionConverterMock = mock(TransactionConverter::class.java)
        converter = TransactionListConverter(
                accountTypeServiceMock,
                customerServiceMock,
                idsExtractorMock,
                transactionConverterMock)
    }

    @Test
    fun shouldReturnTransactionDtoList(){
        //given:
        val accountTypeId1 = 3L
        val accountTypeName1 = "accountType3"
        val customerId1 = 4L
        val customerFirstName1 = "first1"
        val customerLastName1 = "last1"
        val accountTypeId2 = 13L
        val accountTypeName2 = "accountType13"
        val customerId2 = 14L
        val customerFirstName2 = "first14"
        val customerLastName2 = "last14"
        val transactionDao1 = TransactionDao(1L, 2, accountTypeId1, customerId1, Instant.now())
        val transactionDao2 = TransactionDao(2L, 12, accountTypeId2, customerId2, Instant.now())
        val transactionDaoList = listOf(transactionDao1, transactionDao2)
        val accountTypeIdSet = HashSet<Long>()
        accountTypeIdSet.add(5L)
        val customerIdSet = HashSet<Long>()
        customerIdSet.add(6L)
        val idSets = IdSets(accountTypeIdSet, customerIdSet)
        `when`(idsExtractorMock.getIdSets(transactionDaoList)).thenReturn(idSets)
        val accountTypeMap = HashMap<Long, AccountTypeDao>()
        accountTypeMap[accountTypeId1] = AccountTypeDao(accountTypeId1, accountTypeName1)
        accountTypeMap[accountTypeId2] = AccountTypeDao(accountTypeId2, accountTypeName2)
        `when`(accountTypeServiceMock.getAccountTypeMap(idSets.accountTypeIdSet)).thenReturn(accountTypeMap)
        val customerMap = HashMap<Long, CustomerDao>()
        customerMap[customerId1] = CustomerDao(customerId1, customerFirstName1, customerLastName1, 9)
        customerMap[customerId2] = CustomerDao(customerId2, customerFirstName2, customerLastName2, 9)
        `when`(customerServiceMock.getCustomerMap(idSets.customerIdSet)).thenReturn(customerMap)
        val transactionDto1 = TransactionDto("", 1L, "","", "", "")
        `when`(transactionConverterMock.toTransactionDto(
                transactionDao1,
                accountTypeName1,
                customerFirstName1,
                customerLastName1)).thenReturn(transactionDto1)
        val transactionDto2 = TransactionDto("", 2L, "","", "", "")
        `when`(transactionConverterMock.toTransactionDto(
                transactionDao2,
                accountTypeName2,
                customerFirstName2,
                customerLastName2)).thenReturn(transactionDto2)

        //when:
        val actual = converter.toTransactionDto(transactionDaoList)

        //then:
        assertEquals(2, actual.size)
        assertTrue(actual.contains(transactionDto1))
        assertTrue(actual.contains(transactionDto2))
    }

    @Test
    fun shouldNotReturnTransactionDtoWhenHasNoAccountType(){
        //given:
        val accountTypeId1 = 3L
        val accountTypeName1 = "accountType3"
        val customerId1 = 4L
        val customerFirstName1 = "first1"
        val customerLastName1 = "last1"
        val accountTypeId2 = 13L
        val accountTypeName2 = "accountType13"
        val customerId2 = 14L
        val customerFirstName2 = "first14"
        val customerLastName2 = "last14"
        val transactionDao1 = TransactionDao(1L, 2, accountTypeId1, customerId1, Instant.now())
        val transactionDao2 = TransactionDao(2L, 12, accountTypeId2, customerId2, Instant.now())
        val transactionDaoList = listOf(transactionDao1, transactionDao2)
        val accountTypeIdSet = HashSet<Long>()
        accountTypeIdSet.add(5L)
        val customerIdSet = HashSet<Long>()
        customerIdSet.add(6L)
        val idSets = IdSets(accountTypeIdSet, customerIdSet)
        `when`(idsExtractorMock.getIdSets(transactionDaoList)).thenReturn(idSets)
        val accountTypeMap = HashMap<Long, AccountTypeDao>()
        accountTypeMap[accountTypeId2] = AccountTypeDao(accountTypeId2, accountTypeName2)
        `when`(accountTypeServiceMock.getAccountTypeMap(idSets.accountTypeIdSet)).thenReturn(accountTypeMap)
        val customerMap = HashMap<Long, CustomerDao>()
        customerMap[customerId1] = CustomerDao(customerId1, customerFirstName1, customerLastName1, 9)
        customerMap[customerId2] = CustomerDao(customerId2, customerFirstName2, customerLastName2, 9)
        `when`(customerServiceMock.getCustomerMap(idSets.customerIdSet)).thenReturn(customerMap)
        val transactionDto1 = TransactionDto("", 1L, "","", "", "")
        `when`(transactionConverterMock.toTransactionDto(
                transactionDao1,
                accountTypeName1,
                customerFirstName1,
                customerLastName1)).thenReturn(transactionDto1)
        val transactionDto2 = TransactionDto("", 2L, "","", "", "")
        `when`(transactionConverterMock.toTransactionDto(
                transactionDao2,
                accountTypeName2,
                customerFirstName2,
                customerLastName2)).thenReturn(transactionDto2)

        //when:
        val actual = converter.toTransactionDto(transactionDaoList)

        //then:
        assertEquals(1, actual.size)
        assertTrue(actual.contains(transactionDto2))
    }

    @Test
    fun shouldNotReturnTransactionDtoWhenHasNoCustomer(){
        //given:
        val accountTypeId1 = 3L
        val accountTypeName1 = "accountType3"
        val customerId1 = 4L
        val customerFirstName1 = "first1"
        val customerLastName1 = "last1"
        val accountTypeId2 = 13L
        val accountTypeName2 = "accountType13"
        val customerId2 = 14L
        val customerFirstName2 = "first14"
        val customerLastName2 = "last14"
        val transactionDao1 = TransactionDao(1L, 2, accountTypeId1, customerId1, Instant.now())
        val transactionDao2 = TransactionDao(2L, 12, accountTypeId2, customerId2, Instant.now())
        val transactionDaoList = listOf(transactionDao1, transactionDao2)
        val accountTypeIdSet = HashSet<Long>()
        accountTypeIdSet.add(5L)
        val customerIdSet = HashSet<Long>()
        customerIdSet.add(6L)
        val idSets = IdSets(accountTypeIdSet, customerIdSet)
        `when`(idsExtractorMock.getIdSets(transactionDaoList)).thenReturn(idSets)
        val accountTypeMap = HashMap<Long, AccountTypeDao>()
        accountTypeMap[accountTypeId1] = AccountTypeDao(accountTypeId1, accountTypeName1)
        accountTypeMap[accountTypeId2] = AccountTypeDao(accountTypeId2, accountTypeName2)
        `when`(accountTypeServiceMock.getAccountTypeMap(idSets.accountTypeIdSet)).thenReturn(accountTypeMap)
        val customerMap = HashMap<Long, CustomerDao>()
        customerMap[customerId2] = CustomerDao(customerId2, customerFirstName2, customerLastName2, 9)
        `when`(customerServiceMock.getCustomerMap(idSets.customerIdSet)).thenReturn(customerMap)
        val transactionDto1 = TransactionDto("", 1L, "","", "", "")
        `when`(transactionConverterMock.toTransactionDto(
                transactionDao1,
                accountTypeName1,
                customerFirstName1,
                customerLastName1)).thenReturn(transactionDto1)
        val transactionDto2 = TransactionDto("", 2L, "","", "", "")
        `when`(transactionConverterMock.toTransactionDto(
                transactionDao2,
                accountTypeName2,
                customerFirstName2,
                customerLastName2)).thenReturn(transactionDto2)

        //when:
        val actual = converter.toTransactionDto(transactionDaoList)

        //then:
        assertEquals(1, actual.size)
        assertTrue(actual.contains(transactionDto2))
    }
}
