package pl.komorowskidev.kotlinrest.util.converters

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CumstomerConverterTest {

    private lateinit var converter: CustomerConverter

    @BeforeTest
    fun init(){
        converter = CustomerConverter()
    }

    @Test
    fun shouldReturnEntity(){
        //given:
        val id = 1L
        val firstName = "first"
        val lastName = "last"
        val balanceInteger = 1
        val balanceFraction = 43
        val line = "$id,$firstName,$lastName,\"$balanceInteger,$balanceFraction\""

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual != null)
        assertEquals(id, actual.id)
        assertEquals(firstName, actual.firstName)
        assertEquals(lastName, actual.lastName)
        assertEquals(balanceInteger * 100 + balanceFraction, actual.lastLoginBalanceInCents)
    }

    @Test
    fun shouldReturnNullWhenColumnNumberIsLessThan5(){
        //given:
        val id = 1L
        val firstName = "first"
        val lastName = "last"
        val balanceInteger = 1
        val line = "$id,$firstName,$lastName,\"$balanceInteger\""

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenColumnNumberIsMoreThan5(){
        //given:
        val id = 1L
        val firstName = "first"
        val lastName = "last"
        val balanceInteger = 1
        val balanceFraction = 43
        val line = "$id,$firstName,$lastName,\"$balanceInteger,$balanceFraction\",another"

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenIdIsNotNumber(){
        //given:
        val id = "one"
        val firstName = "first"
        val lastName = "last"
        val balanceInteger = 1
        val balanceFraction = 43
        val line = "$id,$firstName,$lastName,\"$balanceInteger,$balanceFraction\""

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenBalanceIntegerIsNotNumber(){
        //given:
        val id = 1
        val firstName = "first"
        val lastName = "last"
        val balanceInteger = "one"
        val balanceFraction = 43
        val line = "$id,$firstName,$lastName,\"$balanceInteger,$balanceFraction\""

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }

    @Test
    fun shouldReturnNullWhenBalanceFractionIsNotNumber(){
        //given:
        val id = 1
        val firstName = "first"
        val lastName = "last"
        val balanceInteger = 1
        val balanceFraction = "some number"
        val line = "$id,$firstName,$lastName,\"$balanceInteger,$balanceFraction\""

        //when:
        val actual = converter.dtoToEntity(line)

        //then:
        assertTrue(actual == null)
    }
}