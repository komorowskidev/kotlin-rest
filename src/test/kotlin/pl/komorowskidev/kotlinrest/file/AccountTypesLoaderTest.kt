package pl.komorowskidev.kotlinrest.file

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.komorowskidev.kotlinrest.properties.ColumnName
import pl.komorowskidev.kotlinrest.properties.DataFilePathName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountTypesLoaderTest {

    private lateinit var loader: AccountTypesLoader
    private lateinit var dataFilePathNameMock: DataFilePathName
    private lateinit var columnNameMock: ColumnName
    private lateinit var fileHelperMock: FileHelper

    @BeforeTest
    fun init(){
        dataFilePathNameMock = mock(DataFilePathName::class.java)
        columnNameMock= mock(ColumnName::class.java)
        fileHelperMock= mock(FileHelper::class.java)
        loader = AccountTypesLoader(dataFilePathNameMock, columnNameMock, fileHelperMock)
    }

    @Test
    fun shouldReturnListOfAccountType(){
        //given:
        val path = "path"
        val firstLine = "first line"
        val id1 = 10L
        val id2 = 11L
        val name1 = "line2"
        val name2 = "line3"
        val line2 = "$id1,$name1"
        val line3 = "$id2,$name2"
        val lines = listOf(firstLine, line2, line3)
        `when`(dataFilePathNameMock.accountTypes).thenReturn(path)
        `when`(fileHelperMock.getLines(path)).thenReturn(lines)
        `when`(columnNameMock.accountTypes).thenReturn(firstLine)

        //when:
        val actual = loader.getAccountTypes()

        //then:
        assertEquals(2, actual.size)
        assertEquals(id1, actual[0].id)
        assertEquals(name1, actual[0].name)
        assertEquals(id2, actual[1].id)
        assertEquals(name2, actual[1].name)
    }

    @Test
    fun shouldReturnEmptyListOfAccountTypeWhenFileHelperReturnEmptyList(){
        //given:
        val path = "path"
        val lines = emptyList<String>()
        `when`(dataFilePathNameMock.accountTypes).thenReturn(path)
        `when`(fileHelperMock.getLines(path)).thenReturn(lines)

        //when:
        val actual = loader.getAccountTypes()

        //then:
        assert(actual.isEmpty())
    }

    @Test
    fun shouldReturnListOfAccountTypeButShouldOmitErrorLines(){
        //given:
        val path = "path"
        val firstLine = "first line"
        val id1 = 10L
        val id2 = 11L
        val name1 = "line2"
        val name2 = "line3"
        val line2 = "$id1$name1"
        val line3 = "$id2,$name2"
        val lines = listOf(firstLine, line2, line3)
        `when`(dataFilePathNameMock.accountTypes).thenReturn(path)
        `when`(fileHelperMock.getLines(path)).thenReturn(lines)
        `when`(columnNameMock.accountTypes).thenReturn(firstLine)

        //when:
        val actual = loader.getAccountTypes()

        //then:
        assertEquals(1, actual.size)
        assertEquals(id2, actual[0].id)
        assertEquals(name2, actual[0].name)
    }
}
