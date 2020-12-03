package pl.komorowskidev.kotlinrest.db.services

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entities.Entity
import pl.komorowskidev.kotlinrest.db.entities.TransactionEntity
import pl.komorowskidev.kotlinrest.db.repository.TransactionRepository
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import pl.komorowskidev.kotlinrest.util.IdsExtractor
import pl.komorowskidev.kotlinrest.util.converters.TransactionConverter

@Component
class TransactionService(
        private val repository: TransactionRepository,
        private val idsExtractor: IdsExtractor,
        private val transactionConverter: TransactionConverter): Service {

    override fun save(entity: Entity){
        repository.save(entity as TransactionEntity)
    }

    fun getTransactionsDto(accountTypeId: String, customerId: String): List<TransactionDto> {
        val result = ArrayList<TransactionDto>()
        val entityList: List<TransactionEntity>
        if(accountTypeId.toLowerCase() == "all"){
            if(customerId.toLowerCase() == "all"){
                entityList = repository.findAllByOrderByAmountInCentsAsc()
            } else {
                val customerIdSet = idsExtractor.getIdSet(customerId)
                entityList = repository.findByCustomerIdInOrderByAmountInCentsAsc(customerIdSet)
            }
        } else {
            val accountTypeIdSet = idsExtractor.getIdSet(accountTypeId)
            if(customerId.toLowerCase() == ("all")){
                entityList = repository.findByAccountTypeIdInOrderByAmountInCentsAsc(accountTypeIdSet)
            } else {
                val customerIdSet = idsExtractor.getIdSet(customerId)
                entityList = repository.findByCustomerIdInAndAccountTypeIdInOrderByAmountInCentsAsc(
                        customerIdSet,
                        accountTypeIdSet)
            }
        }
        entityList.forEach {
            result.add(transactionConverter.entityToDto(it))
        }
        return result
    }

}
