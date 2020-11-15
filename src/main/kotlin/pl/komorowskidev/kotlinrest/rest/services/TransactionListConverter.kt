package pl.komorowskidev.kotlinrest.rest.services

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entity.AccountType
import pl.komorowskidev.kotlinrest.db.entity.Customer
import pl.komorowskidev.kotlinrest.db.entity.Transaction
import pl.komorowskidev.kotlinrest.db.repository.AccountTypeRepository
import pl.komorowskidev.kotlinrest.db.repository.CustomerRepository
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import java.lang.RuntimeException

@Component
class TransactionListConverter(
    private val transactionConverter: TransactionConverter,
    private val accountTypeRepository: AccountTypeRepository,
    private val customerRepository: CustomerRepository) {

    fun toTransactionDto(transactionList: List<Transaction>): List<TransactionDto> {
        val transactionDtoList = ArrayList<TransactionDto>()
        val iterator = transactionList.iterator()
        val accountTypeMap = HashMap<Long, String>()
        val customerMap = HashMap<Long, Customer>()
        while(iterator.hasNext()){
            val transaction = iterator.next()
            val accountTypeName = getAccountTypeName(transaction.accountTypeId, accountTypeMap)
            val customer = getCustomer(transaction.customerId, customerMap)
            transactionDtoList.add(transactionConverter.toTransactionDto(
                transaction,
                accountTypeName,
                customer.firstName,
                customer.lastName))
        }
        return transactionDtoList
    }

    private fun getCustomer(customerId: Long, customerMap: HashMap<Long, Customer>): Customer {
        val result: Customer
        var customer = customerMap.get(customerId)
        if(customer == null) {
            val customerOptional = customerRepository.findById(customerId)
            result = customerOptional.orElseThrow()
            customerMap.put(customerId, result)
        } else {
            result = customer
        }
        return result
    }

    private fun getAccountTypeName(accountTypeId: Long, accountTypeMap: HashMap<Long, String>): String {
        var name = accountTypeMap.get(accountTypeId)
        if (name == null) {
            val accountTypeOptional = accountTypeRepository.findById(accountTypeId)
            val accountType = accountTypeOptional.orElseThrow()
            name = accountType.name
            accountTypeMap.put(accountType.id, name)
        }
        return name;
    }

}