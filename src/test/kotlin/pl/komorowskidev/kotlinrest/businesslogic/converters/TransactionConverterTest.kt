package pl.komorowskidev.kotlinrest.businesslogic.converters

import pl.komorowskidev.kotlinrest.db.dao.TransactionDao
import java.time.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionConverterTest {

    private lateinit var converter: TransactionConverter

    @BeforeTest
    fun init(){
        converter = TransactionConverter()
    }

    @Test
    fun shouldConvertTransactionDaoToTransactionDto(){
        //given:
        val transactionId = 1L
        val amountInCents = 20145
        val expectedAmount = "201.45"
        val accountTypeId = 3L
        val customerId = 4L
        val date = Instant.parse("2020-11-22T12:07:16.00Z")
        val expectedDate = "2020-11-22 12:07:16"
        val transactionDao = TransactionDao(transactionId, amountInCents, accountTypeId, customerId, date)
        val accountTypeName = "name"
        val customerFirstName = "first"
        val customerLastName = "last"

        //when:
        val actual = converter.toTransactionDto(transactionDao, accountTypeName, customerFirstName, customerLastName)

        //then:
        assertEquals(expectedDate, actual.utc)
        assertEquals(transactionId, actual.id)
        assertEquals(expectedAmount, actual.amount)
        assertEquals(accountTypeName, actual.accountType)
        assertEquals(customerFirstName, actual.firstName)
        assertEquals(customerLastName, actual.lastName)
    }

    @Test
    fun shouldConvertTransactionDaoToTransactionDtoAmountZeroCents(){
        //given:
        val amountInCents = 200
        val expectedAmount = "2.00"
        val transactionDao = TransactionDao(1L, amountInCents, 3L, 4L, Instant.now())

        //when:
        val actual = converter.toTransactionDto(transactionDao, "", "", "")

        //then:
        assertEquals(expectedAmount, actual.amount)
    }

    @Test
    fun shouldConvertTransactionDaoToTransactionDtoAmountFewCents(){
        //given:
        val amountInCents = 2
        val expectedAmount = "0.02"
        val transactionDao = TransactionDao(1L, amountInCents, 3L, 4L, Instant.now())

        //when:
        val actual = converter.toTransactionDto(transactionDao, "", "", "")

        //then:
        assertEquals(expectedAmount, actual.amount)
    }

    @Test
    fun shouldConvertTransactionDaoToTransactionDtoAmountZero(){
        //given:
        val amountInCents = 0
        val expectedAmount = "0.00"
        val transactionDao = TransactionDao(1L, amountInCents, 3L, 4L, Instant.now())

        //when:
        val actual = converter.toTransactionDto(transactionDao, "", "", "")

        //then:
        assertEquals(expectedAmount, actual.amount)
    }

    @Test
    fun shouldConvertTransactionDaoToTransactionDtoNegativeAmount(){
        //given:
        val amountInCents = -1030
        val expectedAmount = "-10.30"
        val transactionDao = TransactionDao(1L, amountInCents, 3L, 4L, Instant.now())

        //when:
        val actual = converter.toTransactionDto(transactionDao, "", "", "")

        //then:
        assertEquals(expectedAmount, actual.amount)
    }
}
