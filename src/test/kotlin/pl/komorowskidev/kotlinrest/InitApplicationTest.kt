package pl.komorowskidev.kotlinrest

import com.mongodb.client.MongoDatabase
import org.mockito.Mockito.*
import org.springframework.data.mongodb.core.MongoTemplate
import pl.komorowskidev.kotlinrest.db.dao.AccountTypeDao
import pl.komorowskidev.kotlinrest.db.dao.CustomerDao
import pl.komorowskidev.kotlinrest.db.dao.TransactionDao
import pl.komorowskidev.kotlinrest.db.repository.AccountTypeRepository
import pl.komorowskidev.kotlinrest.db.repository.CustomerRepository
import pl.komorowskidev.kotlinrest.db.repository.TransactionRepository
import pl.komorowskidev.kotlinrest.file.AccountTypesLoader
import pl.komorowskidev.kotlinrest.file.CustomersLoader
import pl.komorowskidev.kotlinrest.file.TransactionsLoader
import java.time.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test

class InitApplicationTest {
    private lateinit var initApplication: InitApplication
    private lateinit var mongoTemplateMock: MongoTemplate
    private lateinit var accountTypesLoaderMock: AccountTypesLoader
    private lateinit var customersLoaderMock: CustomersLoader
    private lateinit var transactionsLoaderMock: TransactionsLoader
    private lateinit var accountTypeRepositoryMock: AccountTypeRepository
    private lateinit var customerRepositoryMock: CustomerRepository
    private lateinit var transactionRepositoryMock: TransactionRepository
    private lateinit var mongoDatabaseMock: MongoDatabase
    private lateinit var accountTypeDao1: AccountTypeDao
    private lateinit var accountTypeDao2: AccountTypeDao
    private lateinit var customerDao1: CustomerDao
    private lateinit var customerDao2: CustomerDao
    private lateinit var transactionDao1: TransactionDao
    private lateinit var transactionDao2: TransactionDao

    @BeforeTest
    fun init(){
        mongoTemplateMock = mock(MongoTemplate::class.java)
        accountTypesLoaderMock = mock(AccountTypesLoader::class.java)
        customersLoaderMock = mock(CustomersLoader::class.java)
        transactionsLoaderMock = mock(TransactionsLoader::class.java)
        accountTypeRepositoryMock = mock(AccountTypeRepository::class.java)
        customerRepositoryMock = mock(CustomerRepository::class.java)
        transactionRepositoryMock = mock(TransactionRepository::class.java)
        mongoDatabaseMock = mock(MongoDatabase::class.java)
        `when`(mongoTemplateMock.db).thenReturn(mongoDatabaseMock)
        accountTypeDao1 = AccountTypeDao(1L, "1")
        accountTypeDao2 = AccountTypeDao(2L, "2")
        `when`(accountTypesLoaderMock.getAccountTypes()).thenReturn(listOf(accountTypeDao1, accountTypeDao2))
        customerDao1 = CustomerDao(3L,"3", "4", 5)
        customerDao2 = CustomerDao(6L,"7", "8", 9)
        `when`(customersLoaderMock.getCustomers()).thenReturn(listOf(customerDao1, customerDao2))
        transactionDao1 = TransactionDao(10L,1234,11L,12L, Instant.now())
        transactionDao2 = TransactionDao(13L,12345,14L,16L, Instant.now())
        `when`(transactionsLoaderMock.getTransactions()).thenReturn(listOf(transactionDao1, transactionDao2))
        initApplication = InitApplication(
            mongoTemplateMock,
            accountTypesLoaderMock,
            customersLoaderMock,
            transactionsLoaderMock,
            accountTypeRepositoryMock,
            customerRepositoryMock,
            transactionRepositoryMock)
    }

    @Test
    fun shouldClearDataBase(){
        //then
        verify(mongoDatabaseMock).drop()
    }

    @Test
    fun shouldSendDataTypesToDataBase(){
        //then
        verify(accountTypeRepositoryMock).save(accountTypeDao1)
        verify(accountTypeRepositoryMock).save(accountTypeDao2)
    }

    @Test
    fun shouldSendCustomersToDataBase(){
        //then
        verify(customerRepositoryMock).save(customerDao1)
        verify(customerRepositoryMock).save(customerDao2)
    }

    @Test
    fun shouldSendTransactionsToDataBase(){
        //then
        verify(transactionRepositoryMock).save(transactionDao1)
        verify(transactionRepositoryMock).save(transactionDao2)
    }
}
