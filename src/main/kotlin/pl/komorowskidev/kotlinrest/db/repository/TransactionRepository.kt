package pl.komorowskidev.kotlinrest.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import pl.komorowskidev.kotlinrest.db.entity.Transaction

interface TransactionRepository : MongoRepository<Transaction, String> {

    fun findAllByOrderByAmountInCentsAsc(): List<Transaction>

    fun findByCustomerIdInOrderByAmountInCentsAsc(
        customerIdList: List<Long>): List<Transaction>

    fun findByAccountTypeIdInOrderByAmountInCentsAsc(
        accountTypeIdList: List<Long>): List<Transaction>

    fun findByCustomerIdInAndAccountTypeIdInOrderByAmountInCentsAsc(
        customerIdList: List<Long>, accountTypeIdList: List<Long>): List<Transaction>
}