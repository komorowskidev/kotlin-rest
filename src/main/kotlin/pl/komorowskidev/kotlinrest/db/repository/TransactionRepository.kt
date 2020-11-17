package pl.komorowskidev.kotlinrest.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import pl.komorowskidev.kotlinrest.db.entity.Transaction

interface TransactionRepository : MongoRepository<Transaction, String> {

    fun findAllByOrderByAmountInCentsAsc(): List<Transaction>

    fun findByCustomerIdInOrderByAmountInCentsAsc(
        customerIdSet: Set<Long>): List<Transaction>

    fun findByAccountTypeIdInOrderByAmountInCentsAsc(
        accountTypeIdSet: Set<Long>): List<Transaction>

    fun findByCustomerIdInAndAccountTypeIdInOrderByAmountInCentsAsc(
        customerIdSet: Set<Long>, accountTypeIdSet: Set<Long>): List<Transaction>
}