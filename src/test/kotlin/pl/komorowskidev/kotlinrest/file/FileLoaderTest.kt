package pl.komorowskidev.kotlinrest.file

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import pl.komorowskidev.kotlinrest.InitDataProcessor
import pl.komorowskidev.kotlinrest.db.services.AccountTypeService
import pl.komorowskidev.kotlinrest.util.converters.AccountTypeConverter
import java.io.IOException
import kotlin.test.BeforeTest
import kotlin.test.Test

class FileLoaderTest {

    private lateinit var loader: FileLoader
    private lateinit var initDataProcessorMock: InitDataProcessor

    @BeforeTest
    fun init() {
        initDataProcessorMock = mock(InitDataProcessor::class.java)
        loader = FileLoader(initDataProcessorMock)
    }

    @Test
    fun shouldCallConverterAndServiceWhenFoundFile() {
        //given:
        val pathName = "src/test/resources/file-test.txt"
        val converterMock = mock(AccountTypeConverter::class.java)
        val serviceMock = mock(AccountTypeService::class.java)

        //when:
        loader.load(pathName, converterMock, serviceMock)

        //then:
        verify(initDataProcessorMock).convertAndInsertIntoDatabase(converterMock, serviceMock, "1")
        verify(initDataProcessorMock).convertAndInsertIntoDatabase(converterMock, serviceMock, "2")
        verify(initDataProcessorMock).convertAndInsertIntoDatabase(converterMock, serviceMock, "3")
    }

    @Test(expected = IOException::class)
    fun shouldThrowsExceptionWhenFileNotFound(){
        //given:
        val pathName = "unknown"
        val converterMock = mock(AccountTypeConverter::class.java)
        val serviceMock = mock(AccountTypeService::class.java)

        //when:
        loader.load(pathName, converterMock, serviceMock)
    }
}