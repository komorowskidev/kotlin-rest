package pl.komorowskidev.kotlinrest.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import pl.komorowskidev.kotlinrest.db.dao.TransactionDao

interface TransactionRepository : MongoRepository<TransactionDao, String> {

    fun findAllByOrderByAmountInCentsAsc(): List<TransactionDao>

    fun findByCustomerIdInOrderByAmountInCentsAsc(
        customerIdSet: Set<Long>): List<TransactionDao>

    fun findByAccountTypeIdInOrderByAmountInCentsAsc(
        accountTypeIdSet: Set<Long>): List<TransactionDao>

    fun findByCustomerIdInAndAccountTypeIdInOrderByAmountInCentsAsc(
        customerIdSet: Set<Long>, accountTypeIdSet: Set<Long>): List<TransactionDao>
}
