package pl.komorowskidev.kotlinrest.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import pl.komorowskidev.kotlinrest.db.entities.TransactionEntity

interface TransactionRepository: MongoRepository<TransactionEntity, Long> {

    fun findAllByOrderByAmountInCentsAsc(): List<TransactionEntity>

    fun findByCustomerIdInOrderByAmountInCentsAsc(
        customerIdSet: Set<Long>): List<TransactionEntity>

    fun findByAccountTypeIdInOrderByAmountInCentsAsc(
        accountTypeIdSet: Set<Long>): List<TransactionEntity>

    fun findByCustomerIdInAndAccountTypeIdInOrderByAmountInCentsAsc(
        customerIdSet: Set<Long>, accountTypeIdSet: Set<Long>): List<TransactionEntity>
}
