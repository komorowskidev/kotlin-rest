package pl.komorowskidev.kotlinrest.file

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.komorowskidev.kotlinrest.properties.ColumnName
import pl.komorowskidev.kotlinrest.properties.DataFilePathName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomerLoaderTest {

    private lateinit var loader: CustomersLoader
    private lateinit var dataFilePathNameMock: DataFilePathName
    private lateinit var columnNameMock: ColumnName
    private lateinit var fileHelperMock: FileHelper

    @BeforeTest
    fun init(){
        dataFilePathNameMock = mock(DataFilePathName::class.java)
        columnNameMock= mock(ColumnName::class.java)
        fileHelperMock= mock(FileHelper::class.java)
        loader = CustomersLoader(dataFilePathNameMock, columnNameMock, fileHelperMock)
    }

    @Test
    fun shouldReturnListOfCustomer(){
        //given:
        val path = "path"
        val firstLine = "first line"
        val id1 = 10L
        val id2 = 11L
        val firstName1 = "line2"
        val firstName2 = "line3"
        val lastName1 = "last2"
        val lastName2 = "last3"
        val balance1 = "10,12"
        val expectedBalance1 = 1012
        val balance2 = "13,14"
        val expectedBalance2 = 1314
        val line2 = "$id1,$firstName1,$lastName1,$balance1"
        val line3 = "$id2,$firstName2,$lastName2,$balance2"
        val lines = listOf(firstLine, line2, line3)
        `when`(dataFilePathNameMock.customers).thenReturn(path)
        `when`(fileHelperMock.getLines(path)).thenReturn(lines)
        `when`(columnNameMock.customers).thenReturn(firstLine)

        //when:
        val actual = loader.getCustomers()

        //then:
        assertEquals(2, actual.size)
        assertEquals(id1, actual[0].id)
        assertEquals(firstName1, actual[0].firstName)
        assertEquals(lastName1, actual[0].lastName)
        assertEquals(expectedBalance1, actual[0].lastLoginBalanceInCents)
        assertEquals(id2, actual[1].id)
        assertEquals(firstName2, actual[1].firstName)
        assertEquals(lastName2, actual[1].lastName)
        assertEquals(expectedBalance2, actual[1].lastLoginBalanceInCents)
    }

    @Test
    fun shouldReturnEmptyListOfCustomersWhenFileHelperReturnEmptyList(){
        //given:
        val path = "path"
        val lines = emptyList<String>()
        `when`(dataFilePathNameMock.customers).thenReturn(path)
        `when`(fileHelperMock.getLines(path)).thenReturn(lines)

        //when:
        val actual = loader.getCustomers()

        //then:
        assert(actual.isEmpty())
    }

    @Test
    fun shouldReturnListOfCustomersButShouldOmitErrorLines(){
        //given:
        val path = "path"
        val firstLine = "first line"
        val id1 = 10L
        val id2 = 11L
        val firstName1 = "line2"
        val firstName2 = "line3"
        val lastName1 = "last2"
        val lastName2 = "last3"
        val balance1 = "10,12"
        val balance2 = "13,14"
        val expectedBalance2 = 1314
        val line2 = "$id1,$firstName1$lastName1,$balance1"
        val line3 = "$id2,$firstName2,$lastName2,$balance2"
        val lines = listOf(firstLine, line2, line3)
        `when`(dataFilePathNameMock.customers).thenReturn(path)
        `when`(fileHelperMock.getLines(path)).thenReturn(lines)
        `when`(columnNameMock.customers).thenReturn(firstLine)

        //when:
        val actual = loader.getCustomers()

        //then:
        assertEquals(1, actual.size)
        assertEquals(id2, actual[0].id)
        assertEquals(firstName2, actual[0].firstName)
        assertEquals(lastName2, actual[0].lastName)
        assertEquals(expectedBalance2, actual[0].lastLoginBalanceInCents)
    }
}
