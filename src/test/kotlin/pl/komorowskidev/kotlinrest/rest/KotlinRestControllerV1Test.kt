package pl.komorowskidev.kotlinrest.rest

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.komorowskidev.kotlinrest.db.services.TransactionService
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class KotlinRestControllerV1Test {

    private lateinit var serviceMock: TransactionService
    private lateinit var controller: KotlinRestControllerV1

    @BeforeTest
    fun init(){
        serviceMock = mock(TransactionService::class.java)
        controller = KotlinRestControllerV1(serviceMock)
    }

    @Test
    fun shouldSendArgumentsToTransactionServiceAndReturnResult(){
        //given
        val accountTypeId = "1"
        val customerId = "2"
        val transactionDtoList = ArrayList<TransactionDto>()
        transactionDtoList.add(TransactionDto("",3L, "", "", "", ""))
        `when`(serviceMock.getTransactionsDto(accountTypeId, customerId)).thenReturn(transactionDtoList)

        //when
        val actual = controller.transaction(accountTypeId, customerId)

        //then
        assertEquals(transactionDtoList, actual)
    }
}
