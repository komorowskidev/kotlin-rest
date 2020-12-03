package pl.komorowskidev.kotlinrest

import org.mockito.Mockito.*
import pl.komorowskidev.kotlinrest.db.services.AccountTypeService
import pl.komorowskidev.kotlinrest.db.services.CustomerService
import pl.komorowskidev.kotlinrest.db.services.DbService
import pl.komorowskidev.kotlinrest.db.services.TransactionService
import pl.komorowskidev.kotlinrest.file.FileLoader
import pl.komorowskidev.kotlinrest.properties.DataFilePathName
import pl.komorowskidev.kotlinrest.util.converters.AccountTypeConverter
import pl.komorowskidev.kotlinrest.util.converters.CustomerConverter
import pl.komorowskidev.kotlinrest.util.converters.TransactionConverter
import kotlin.test.BeforeTest
import kotlin.test.Test

class InitApplicationTest {
    private lateinit var initApplication: InitApplication
    private lateinit var dbServiceMock: DbService
    private lateinit var dataFilePathNameMock: DataFilePathName
    private lateinit var accountTypeConverterMock: AccountTypeConverter
    private lateinit var customerConverterMock: CustomerConverter
    private lateinit var transactionConverterMock: TransactionConverter
    private lateinit var fileLoaderMock: FileLoader
    private lateinit var accountTypeServiceMock: AccountTypeService
    private lateinit var customerServiceMock: CustomerService
    private lateinit var transactionServiceMock: TransactionService

    @BeforeTest
    fun init(){
        dbServiceMock = mock(DbService::class.java)
        dataFilePathNameMock = mock(DataFilePathName::class.java)
        accountTypeConverterMock = mock(AccountTypeConverter::class.java)
        customerConverterMock = mock(CustomerConverter::class.java)
        transactionConverterMock = mock(TransactionConverter::class.java)
        fileLoaderMock = mock(FileLoader::class.java)
        accountTypeServiceMock = mock(AccountTypeService::class.java)
        customerServiceMock = mock(CustomerService::class.java)
        transactionServiceMock = mock(TransactionService::class.java)
        `when`(dataFilePathNameMock.accountTypes).thenReturn("acc")
        `when`(dataFilePathNameMock.customers).thenReturn("cus")
        `when`(dataFilePathNameMock.transactions).thenReturn("tra")
        initApplication = InitApplication(
            dbServiceMock,
            dataFilePathNameMock,
            accountTypeConverterMock,
            customerConverterMock,
            transactionConverterMock,
            fileLoaderMock,
            accountTypeServiceMock,
            customerServiceMock,
            transactionServiceMock)
    }

    @Test
    fun shouldClearDataBase(){
        //then
        verify(dbServiceMock).clearDataBase()
    }

    @Test
    fun shouldLoadExampleData(){
        //then
        verify(fileLoaderMock).load(dataFilePathNameMock.accountTypes, accountTypeConverterMock, accountTypeServiceMock)
        verify(fileLoaderMock).load(dataFilePathNameMock.customers, customerConverterMock, customerServiceMock)
        verify(fileLoaderMock).load(dataFilePathNameMock.transactions, transactionConverterMock, transactionServiceMock)
    }

    @Test
    fun shouldRemoveTemporaryCollections(){
        //then
        verify(dbServiceMock).removeTemporaryCollection("account-type")
        verify(dbServiceMock).removeTemporaryCollection("customer")
    }
}

