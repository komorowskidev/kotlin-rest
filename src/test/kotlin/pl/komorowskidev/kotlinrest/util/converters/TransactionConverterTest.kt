package pl.komorowskidev.kotlinrest.util.converters

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.komorowskidev.kotlinrest.db.entities.AccountTypeEntity
import pl.komorowskidev.kotlinrest.db.entities.CustomerEntity
import pl.komorowskidev.kotlinrest.db.entities.TransactionEntity
import pl.komorowskidev.kotlinrest.db.services.AccountTypeService
import pl.komorowskidev.kotlinrest.db.services.CustomerService
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TransactionConverterTest {

    private lateinit var converter: TransactionConverter
    private lateinit var accountTypeServiceMock: AccountTypeService
    private lateinit var customerServiceMock: CustomerService

    @BeforeTest
    fun init(){
        accountTypeServiceMock = mock(AccountTypeService::class.java)
        customerServiceMock = mock(CustomerService::class.java)
        converter = TransactionConverter(accountTypeServiceMock, customerServiceMock)
    }

    @Test
    fun shouldReturnEntity(){
        //given:
        val id = 2L
        val amountInteger = 23
        val amountFraction = 12
        val customerId = 3L
        val customerFirstName = "first"
        val customerLastName = "last"
        val customerBalance = 1234
        val customerEntity = CustomerEntity(customerId, customerFirstName, customerLastName, customerBalance)
        `when`(customerServiceMock.findById(customerId)).thenReturn(Optional.of(customerEntity))
        val accountTypeId = 4L
        val accountTypeName = "acc"
        val accountTypeEntity = AccountTypeEntity(accountTypeId, accountTypeName)
        `when`(accountTypeServiceMock.findById(accountTypeId)).thenReturn(Optional.of(accountTypeEntity))
        val date = "2020-12-03 06:49:24"
        val utc = LocalDateTime
            .parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .atZone(ZoneId.systemDefault())
            .toInstant()
        val line = "$id,\"$amountInteger,$amountFraction\",$accountTypeId,$customerId,$date"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual != null)
        assertEquals(id, actual.id)
        assertEquals(amountInteger * 100 + amountFraction, actual.amountInCents)
        assertEquals(accountTypeId, actual.accountTypeId)
        assertEquals(accountTypeName, actual.accountTypeName)
        assertEquals(customerId, actual.customerId)
        assertEquals(customerFirstName, actual.customerFirstName)
        assertEquals(customerLastName, actual.customerLastName)
        assertEquals(customerBalance, actual.customerLastLoginBalanceInCents)
        assertEquals(utc, actual.utcDate)
    }

    @Test
    fun shouldReturnEntityWithAccountTypeUknownWhenAccountTypeServiceCannotFindById(){
        //given:
        val id = 2L
        val amountInteger = 23
        val amountFraction = 12
        val customerId = 3L
        val customerFirstName = "first"
        val customerLastName = "last"
        val customerBalance = 1234
        val customerEntity = CustomerEntity(customerId, customerFirstName, customerLastName, customerBalance)
        `when`(customerServiceMock.findById(customerId)).thenReturn(Optional.of(customerEntity))
        val accountTypeId = 4L
        `when`(accountTypeServiceMock.findById(accountTypeId)).thenReturn(Optional.empty())
        val date = "2020-12-03 06:49:24"
        val utc = LocalDateTime
            .parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .atZone(ZoneId.systemDefault())
            .toInstant()
        val line = "$id,\"$amountInteger,$amountFraction\",$accountTypeId,$customerId,$date"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual != null)
        assertEquals(id, actual.id)
        assertEquals(amountInteger * 100 + amountFraction, actual.amountInCents)
        assertEquals(accountTypeId, actual.accountTypeId)
        assertEquals("unknown", actual.accountTypeName)
        assertEquals(customerId, actual.customerId)
        assertEquals(customerFirstName, actual.customerFirstName)
        assertEquals(customerLastName, actual.customerLastName)
        assertEquals(customerBalance, actual.customerLastLoginBalanceInCents)
        assertEquals(utc, actual.utcDate)
    }

    @Test
    fun shouldReturnEntityWithCustomerNameUknownWhenCustomerServiceCannotFindById(){
        //given:
        val id = 2L
        val amountInteger = 23
        val amountFraction = 12
        val customerId = 3L
        `when`(customerServiceMock.findById(customerId)).thenReturn(Optional.empty())
        val accountTypeId = 4L
        val accountTypeName = "acc"
        val accountTypeEntity = AccountTypeEntity(accountTypeId, accountTypeName)
        `when`(accountTypeServiceMock.findById(accountTypeId)).thenReturn(Optional.of(accountTypeEntity))
        val date = "2020-12-03 06:49:24"
        val utc = LocalDateTime
            .parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .atZone(ZoneId.systemDefault())
            .toInstant()
        val line = "$id,\"$amountInteger,$amountFraction\",$accountTypeId,$customerId,$date"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual != null)
        assertEquals(id, actual.id)
        assertEquals(amountInteger * 100 + amountFraction, actual.amountInCents)
        assertEquals(accountTypeId, actual.accountTypeId)
        assertEquals(accountTypeName, actual.accountTypeName)
        assertEquals(customerId, actual.customerId)
        assertEquals("unknown", actual.customerFirstName)
        assertEquals("unknown", actual.customerLastName)
        assertEquals(0, actual.customerLastLoginBalanceInCents)
        assertEquals(utc, actual.utcDate)
    }

    @Test
    fun shouldReturnNullWhenIdIsNotNumber(){
        //given:
        val id = "two"
        val amountInteger = 23
        val amountFraction = 12
        val customerId = 3L
        val accountTypeId = 4L
        val date = "2020-12-03 06:49:24"
        val line = "$id,\"$amountInteger,$amountFraction\",$accountTypeId,$customerId,$date"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenAmountIntegerIsNotNumber(){
        //given:
        val id = 2L
        val amountInteger = "twenty"
        val amountFraction = 12
        val customerId = 3L
        val accountTypeId = 4L
        val date = "2020-12-03 06:49:24"
        val line = "$id,\"$amountInteger,$amountFraction\",$accountTypeId,$customerId,$date"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenAmountFractionIsNotNumber(){
        //given:
        val id = 2L
        val amountInteger = 23
        val amountFraction = "twelve"
        val customerId = 3L
        val accountTypeId = 4L
        val date = "2020-12-03 06:49:24"
        val line = "$id,\"$amountInteger,$amountFraction\",$accountTypeId,$customerId,$date"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenCustomerIdIsNotNumber(){
        //given:
        val id = 2L
        val amountInteger = 23
        val amountFraction = 12
        val customerId = "three"
        val accountTypeId = 4L
        val date = "2020-12-03 06:49:24"
        val line = "$id,\"$amountInteger,$amountFraction\",$accountTypeId,$customerId,$date"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenAccountTypeIdIsNotNumber(){
        //given:
        val id = 2L
        val amountInteger = 23
        val amountFraction = 12
        val customerId = 3L
        val accountTypeId = "four"
        val date = "2020-12-03 06:49:24"
        val line = "$id,\"$amountInteger,$amountFraction\",$accountTypeId,$customerId,$date"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenDateNotValid(){
        //given:
        val id = 2L
        val amountInteger = 23
        val amountFraction = 12
        val customerId = 3L
        val accountTypeId = 4L
        val date = "December"
        val line = "$id,\"$amountInteger,$amountFraction\",$accountTypeId,$customerId,$date"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenColumnsNumberIsLessThan6(){
        //given:
        val id =2L
        val amountInteger = 23
        val amountFraction = 12
        val customerId = 3L
        val accountTypeId = 4L
        val line = "$id,\"$amountInteger,$amountFraction\",$accountTypeId,$customerId"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenColumnsNumberIsMoreThan6(){
        //given:
        val id = 2L
        val amountInteger = 23
        val amountFraction = 12
        val customerId = 3L
        val accountTypeId = 4L
        val date = "2020-12-03 06:49:24"
        val line = "$id,\"$amountInteger,$amountFraction\",$accountTypeId,$customerId,$date,another"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnTransactionDto(){
        //given:
        val transactionId = 1L
        val amountInCents = 20145
        val expectedAmount = "201.45"
        val accountTypeId = 3L
        val accountTypeName = "name"
        val customerId = 4L
        val customerFirstName = "first"
        val customerLastName = "last"
        val customerLastLoginBalance = 11
        val utcDate = Instant.parse("2020-11-22T12:07:16.00Z")
        val expectedDate = "2020-11-22 12:07:16"
        val transactionEntity = TransactionEntity(
            transactionId,
            amountInCents,
            accountTypeId,
            accountTypeName,
            customerId,
            customerFirstName,
            customerLastName,
            customerLastLoginBalance,
            utcDate)

        //when:
        val actual = converter.entityToDto(transactionEntity)

        //then:
        assertEquals(expectedDate, actual.utc)
        assertEquals(transactionId, actual.id)
        assertEquals(expectedAmount, actual.amount)
        assertEquals(accountTypeName, actual.accountType)
        assertEquals(customerFirstName, actual.firstName)
        assertEquals(customerLastName, actual.lastName)
    }

    @Test
    fun shouldReturnTransactionDtoAmountZeroCents(){
        //given:
        val amountInCents = 200
        val expectedAmount = "2.00"
        val transactionDao = TransactionEntity(
            1L,
            amountInCents,
            3L,
            "",
            4L,
            "",
            "",
            445,
            Instant.now())

        //when:
        val actual = converter.entityToDto(transactionDao)

        //then:
        assertEquals(expectedAmount, actual.amount)
    }

    @Test
    fun shouldReturnTransactionDtoAmountFewCents(){
        //given:
        val amountInCents = 2
        val expectedAmount = "0.02"
        val transactionDao = TransactionEntity(
            1L,
            amountInCents,
            3L,
            "",
            4L,
            "",
            "",
            445,
            Instant.now())

        //when:
        val actual = converter.entityToDto(transactionDao)

        //then:
        assertEquals(expectedAmount, actual.amount)
    }

    @Test
    fun shouldReturnTransactionDtoAmountZero(){
        //given:
        val amountInCents = 0
        val expectedAmount = "0.00"
        val transactionDao = TransactionEntity(
            1L,
            amountInCents,
            3L,
            "",
            4L,
            "",
            "",
            445,
            Instant.now())

        //when:
        val actual = converter.entityToDto(transactionDao)

        //then:
        assertEquals(expectedAmount, actual.amount)
    }

    @Test
    fun shouldReturnTransactionDtoNegativeAmount(){
        //given:
        val amountInCents = -1030
        val expectedAmount = "-10.30"
        val transactionDao = TransactionEntity(
            1L,
            amountInCents,
            3L,
            "",
            4L,
            "",
            "",
            445,
            Instant.now())

        //when:
        val actual = converter.entityToDto(transactionDao)

        //then:
        assertEquals(expectedAmount, actual.amount)
    }
}
