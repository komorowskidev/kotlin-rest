package pl.komorowskidev.kotlinrest.util

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IdsExtractorTest {

    private lateinit var idsExtractor: IdsExtractor

    @BeforeTest
    fun init(){
        idsExtractor = IdsExtractor()
    }

    @Test
    fun shouldExtractSetOfId(){
        //given:
        val ids = "1,2,3,    ,4,5,a,12,,3,5,8"

        //when:
        val actual = idsExtractor.getIdSet(ids)

        //then:
        assertEquals(7, actual.size)
        assert(actual.contains(1L))
        assert(actual.contains(2L))
        assert(actual.contains(3L))
        assert(actual.contains(4L))
        assert(actual.contains(5L))
        assert(actual.contains(12L))
        assert(actual.contains(8L))
    }

    @Test
    fun shouldReturnEmptySetWhenStringIsEmpty(){
        //given:
        val ids = ""

        //when:
        val actual = idsExtractor.getIdSet(ids)

        //then:
        assert(actual.isEmpty())
    }
}
