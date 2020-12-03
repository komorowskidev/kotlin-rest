package pl.komorowskidev.kotlinrest

import org.mockito.Mockito.*
import pl.komorowskidev.kotlinrest.db.entities.AccountTypeEntity
import pl.komorowskidev.kotlinrest.db.services.AccountTypeService
import pl.komorowskidev.kotlinrest.util.converters.AccountTypeConverter
import kotlin.test.BeforeTest
import kotlin.test.Test

class InitDataProcessorTest {

    private lateinit var initDataProcessor: InitDataProcessor

    @BeforeTest
    fun init(){
        initDataProcessor = InitDataProcessor()
    }

    @Test
    fun shouldSendConvertedDataToDatabase(){
        //given:
        val converterMock = mock(AccountTypeConverter::class.java)
        val serviceMock = mock(AccountTypeService::class.java)
        val line = "line"
        val entity = AccountTypeEntity(1L, "name")
        `when`(converterMock.dtoToEntity(line)).thenReturn(entity)

        //when:
        initDataProcessor.convertAndInsertIntoDatabase(converterMock, serviceMock, line)

        //then:
        verify(serviceMock).save(entity)
    }

    @Test
    fun shouldNotSendConvertedDataToDatabaseWhenConverterReturnsNull(){
        //given:
        val converterMock = mock(AccountTypeConverter::class.java)
        val serviceMock = mock(AccountTypeService::class.java)
        val line = "line"
        `when`(converterMock.dtoToEntity(line)).thenReturn(null)

        //when:
        initDataProcessor.convertAndInsertIntoDatabase(converterMock, serviceMock, line)

        //then:
        verifyNoInteractions(serviceMock)
    }
}