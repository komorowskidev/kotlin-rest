package pl.komorowskidev.kotlinrest.businesslogic.services

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.businesslogic.converters.TransactionListConverter
import pl.komorowskidev.kotlinrest.db.dao.TransactionDao
import pl.komorowskidev.kotlinrest.db.repository.TransactionRepository
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import pl.komorowskidev.kotlinrest.util.IdsExtractor

@Component
class TransactionService(
        private val transactionRepository: TransactionRepository,
        private val transactionListConverter: TransactionListConverter,
        private val idsExtractor: IdsExtractor) {

    fun getTransactions(accountTypeId: String, customerId: String): List<TransactionDto> {
        val result: List<TransactionDao>
        if(accountTypeId.toLowerCase() == "all"){
            if(customerId.toLowerCase() == "all"){
                result = transactionRepository.findAllByOrderByAmountInCentsAsc()
            } else {
                val customerIdSet = idsExtractor.getIdSet(customerId)
                result = transactionRepository.findByCustomerIdInOrderByAmountInCentsAsc(customerIdSet)
            }
        } else {
            val accountTypeIdSet = idsExtractor.getIdSet(accountTypeId)
            if(customerId.toLowerCase() == ("all")){
                result = transactionRepository.findByAccountTypeIdInOrderByAmountInCentsAsc(accountTypeIdSet)
            } else {
                val customerIdSet = idsExtractor.getIdSet(customerId)
                result = transactionRepository.findByCustomerIdInAndAccountTypeIdInOrderByAmountInCentsAsc(
                        customerIdSet,
                        accountTypeIdSet)
            }
        }
        return transactionListConverter.toTransactionDto(result)
    }

}
