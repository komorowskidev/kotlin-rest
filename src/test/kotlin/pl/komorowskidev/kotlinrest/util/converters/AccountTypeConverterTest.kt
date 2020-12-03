package pl.komorowskidev.kotlinrest.util.converters

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AccountTypeConverterTest {

    private lateinit var converter: AccountTypeConverter

    @BeforeTest
    fun init(){
        converter = AccountTypeConverter()
    }

    @Test
    fun shouldReturnEntity(){
        //given:
        val id = 1L
        val name = "name"
        val line = "$id,$name"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual != null)
        assertEquals(id, actual.id)
        assertEquals(name, actual.name)
    }

    @Test
    fun shouldReturnNullWhenColumnNumberIsLessThan2(){
        //given:
        val id = 1L
        val line = "$id"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenColumnNumberIsMoreThan2(){
        //given:
        val id = 1L
        val name = "name"
        val line = "$id,$name,another"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenIdIsNotNumber(){
        //given:
        val id = "one"
        val name = "name"
        val line = "$id,$name"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }
}