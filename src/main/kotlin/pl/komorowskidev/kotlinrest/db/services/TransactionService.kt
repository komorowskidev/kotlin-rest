package pl.komorowskidev.kotlinrest.db.services

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.repository.TransactionRepository
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import pl.komorowskidev.kotlinrest.util.IdsExtractor
import pl.komorowskidev.kotlinrest.util.TransactionListConverter

@Component
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val transactionListConverter: TransactionListConverter,
    private val idsExtractor: IdsExtractor) {

    fun getTransactions(accountTypeId: String, customerId: String): List<TransactionDto> {
        val result: List<TransactionDto>
        if(accountTypeId.toLowerCase().equals("all")){
            if(customerId.toLowerCase().equals("all")){
                result = transactionListConverter.toTransactionDto(
                    transactionRepository.findAllByOrderByAmountInCentsAsc())
            } else {
                val customerIdSet = idsExtractor.getIdSet(customerId)
                result = transactionListConverter.toTransactionDto(
                    transactionRepository.findByCustomerIdInOrderByAmountInCentsAsc(customerIdSet))
            }
        } else {
            val accountTypeIdSet = idsExtractor.getIdSet(accountTypeId)
            if(customerId.toLowerCase().equals("all")){
                result = transactionListConverter.toTransactionDto(
                    transactionRepository.findByAccountTypeIdInOrderByAmountInCentsAsc(accountTypeIdSet))
            } else {
                val customerIdSet = idsExtractor.getIdSet(customerId)
                result = transactionListConverter.toTransactionDto(
                    transactionRepository.findByCustomerIdInAndAccountTypeIdInOrderByAmountInCentsAsc(
                        customerIdSet, accountTypeIdSet))
            }
        }
        return result
    }

}