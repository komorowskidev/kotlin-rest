package pl.komorowskidev.kotlinrest.rest.services

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.repository.TransactionRepository
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto

@Component
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val transactionListConverter: TransactionListConverter) {

    fun getTransactions(accountTypeId: String, customerId: String): List<TransactionDto> {
        val result: List<TransactionDto>
        if(accountTypeId.toLowerCase().equals("all")){
            if(customerId.toLowerCase().equals("all")){
                result = transactionListConverter.toTransactionDto(
                    transactionRepository.findAllByOrderByAmountInCentsAsc())
            } else {
                val customerIdList = getIdList(customerId)
                result = transactionListConverter.toTransactionDto(
                    transactionRepository.findByCustomerIdInOrderByAmountInCentsAsc(customerIdList))
            }
        } else {
            val accountTypeIdList = getIdList(accountTypeId)
            if(customerId.toLowerCase().equals("all")){
                result = transactionListConverter.toTransactionDto(
                    transactionRepository.findByAccountTypeIdInOrderByAmountInCentsAsc(accountTypeIdList))
            } else {
                val customerIdList = getIdList(customerId)
                result = transactionListConverter.toTransactionDto(
                    transactionRepository.findByCustomerIdInAndAccountTypeIdInOrderByAmountInCentsAsc(
                        customerIdList, accountTypeIdList))
            }
        }
        return result;
    }

    private fun getIdList(customerId: String): List<Long> {
        val result = ArrayList<Long>()
        if(customerId.contains(",")){
            val idList = customerId.split(",")
            idList.forEach{
                addToList(result, it)
            }
        } else {
            addToList(result, customerId)
        }
        return result
    }

    private fun addToList(result: ArrayList<Long>, it: String) {
        try {
            result.add(it.toLong())
        } catch(e: NumberFormatException){}
    }
}