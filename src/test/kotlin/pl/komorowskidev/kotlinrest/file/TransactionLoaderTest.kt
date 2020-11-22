package pl.komorowskidev.kotlinrest.file

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.komorowskidev.kotlinrest.properties.ColumnName
import pl.komorowskidev.kotlinrest.properties.DataFilePathName
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionLoaderTest {

    private lateinit var loader: TransactionsLoader
    private lateinit var dataFilePathNameMock: DataFilePathName
    private lateinit var columnNameMock: ColumnName
    private lateinit var fileHelperMock: FileHelper

    @BeforeTest
    fun init(){
        dataFilePathNameMock = mock(DataFilePathName::class.java)
        columnNameMock= mock(ColumnName::class.java)
        fileHelperMock= mock(FileHelper::class.java)
        loader = TransactionsLoader(dataFilePathNameMock, columnNameMock, fileHelperMock)
    }

    @Test
    fun shouldReturnListOfTransactions(){
        //given:
        val path = "path"
        val firstLine = "first line"
        val id1 = 10L
        val id2 = 11L
        val amount1 = "2,10"
        val expectedAmount1 = 210
        val amount2 = "3,12"
        val expectedAmount2 = 312
        val accountType1 = 2L
        val accountType2 = 3L
        val customerId1 = 4L
        val customerId2 = 5L
        val date1 = "2013-08-04 23:57:38"
        val date2 = "2014-09-04 03:22:38"
        val line2 = "$id1,$amount1,$accountType1,$customerId1,$date1"
        val line3 = "$id2,$amount2,$accountType2,$customerId2,$date2"
        val lines = listOf(firstLine, line2, line3)
        `when`(dataFilePathNameMock.transactions).thenReturn(path)
        `when`(fileHelperMock.getLines(path)).thenReturn(lines)
        `when`(columnNameMock.transactions).thenReturn(firstLine)

        //when:
        val actual = loader.getTransactions()

        //then:
        assertEquals(2, actual.size)
        assertEquals(id1, actual[0].id)
        assertEquals(expectedAmount1, actual[0].amountInCents)
        assertEquals(accountType1, actual[0].accountTypeId)
        assertEquals(customerId1, actual[0].customerId)
        assertEquals(
                LocalDateTime.parse(
                        date1,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant(),
                actual[0].date)
        assertEquals(id2, actual[1].id)
        assertEquals(expectedAmount2, actual[1].amountInCents)
        assertEquals(accountType2, actual[1].accountTypeId)
        assertEquals(customerId2, actual[1].customerId)
        assertEquals(
                LocalDateTime.parse(
                        date2,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant(),
                actual[1].date)
    }

    @Test
    fun shouldReturnEmptyListOfTransactionsWhenFileHelperReturnEmptyList(){
        //given:
        val path = "path"
        val lines = emptyList<String>()
        `when`(dataFilePathNameMock.transactions).thenReturn(path)
        `when`(fileHelperMock.getLines(path)).thenReturn(lines)

        //when:
        val actual = loader.getTransactions()

        //then:
        assert(actual.isEmpty())
    }

    @Test
    fun shouldReturnListOfTransactionsButShouldOmitErrorLines(){
        //given:
        val path = "path"
        val firstLine = "first line"
        val id1 = 10L
        val id2 = 11L
        val amount1 = "2,10"
        val amount2 = "3,12"
        val expectedAmount2 = 312
        val accountType1 = 2L
        val accountType2 = 3L
        val customerId1 = 4L
        val customerId2 = 5L
        val date1 = "2013-08-04 23:57:38"
        val date2 = "2014-09-04 03:22:38"
        val line2 = "$id1,$amount1,$accountType1$customerId1,$date1"
        val line3 = "$id2,$amount2,$accountType2,$customerId2,$date2"
        val lines = listOf(firstLine, line2, line3)
        `when`(dataFilePathNameMock.transactions).thenReturn(path)
        `when`(fileHelperMock.getLines(path)).thenReturn(lines)
        `when`(columnNameMock.transactions).thenReturn(firstLine)

        //when:
        val actual = loader.getTransactions()

        //then:
        assertEquals(1, actual.size)
        assertEquals(id2, actual[0].id)
        assertEquals(expectedAmount2, actual[0].amountInCents)
        assertEquals(accountType2, actual[0].accountTypeId)
        assertEquals(customerId2, actual[0].customerId)
        assertEquals(
                LocalDateTime.parse(
                        date2,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant(),
                actual[0].date)
    }
}
