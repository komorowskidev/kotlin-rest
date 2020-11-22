package pl.komorowskidev.kotlinrest.util

import pl.komorowskidev.kotlinrest.db.dao.TransactionDao
import java.time.Instant
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
    fun shouldExtractAccountTypeIdsAndCustomerIds(){
        //given:
        val accountTypeId1 = 10L
        val accountTypeId2 = 20L
        val customerId1 = 15L
        val customerId2 = 115L
        val transactionDao1 = TransactionDao(1L, 100, accountTypeId1, customerId1, Instant.now())
        val transactionDao2 = TransactionDao(2L, 102, accountTypeId2, customerId2, Instant.now())
        val transactionDaoList = listOf(transactionDao1, transactionDao2)

        //when:
        val actual = idsExtractor.getIdSets(transactionDaoList)

        //then:
        assertEquals(2, actual.accountTypeIdSet.size)
        assert(actual.accountTypeIdSet.contains(accountTypeId1))
        assert(actual.accountTypeIdSet.contains(accountTypeId2))
        assertEquals(2, actual.customerIdSet.size)
        assert(actual.customerIdSet.contains(customerId1))
        assert(actual.customerIdSet.contains(customerId2))
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
