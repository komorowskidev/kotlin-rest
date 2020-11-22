package pl.komorowskidev.kotlinrest

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.repository.AccountTypeRepository
import pl.komorowskidev.kotlinrest.db.repository.CustomerRepository
import pl.komorowskidev.kotlinrest.db.repository.TransactionRepository
import pl.komorowskidev.kotlinrest.file.AccountTypesLoader
import pl.komorowskidev.kotlinrest.file.CustomersLoader
import pl.komorowskidev.kotlinrest.file.TransactionsLoader

@Component
class InitApplication(
    private val mongoTemplate: MongoTemplate,
    private val accountTypesLoader: AccountTypesLoader,
    private val customersLoader: CustomersLoader,
    private val transactionsLoader: TransactionsLoader,
    private val accountTypeRepository: AccountTypeRepository,
    private val customerRepository: CustomerRepository,
    private val transactionRepository: TransactionRepository) {

    init {
        clearDataBase()
        insertDataIntoDatabase()
    }

    private fun clearDataBase() {
        mongoTemplate.db.drop()
    }

    private fun insertDataIntoDatabase(){
        accountTypesLoader.getAccountTypes().forEach { accountTypeRepository.save(it) }
        customersLoader.getCustomers().forEach { customerRepository.save(it) }
        transactionsLoader.getTransactions().forEach { transactionRepository.save(it) }
    }
}
