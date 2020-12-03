package pl.komorowskidev.kotlinrest

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.services.AccountTypeService
import pl.komorowskidev.kotlinrest.db.services.CustomerService
import pl.komorowskidev.kotlinrest.db.services.DbService
import pl.komorowskidev.kotlinrest.db.services.TransactionService
import pl.komorowskidev.kotlinrest.file.FileLoader
import pl.komorowskidev.kotlinrest.properties.DataFilePathName
import pl.komorowskidev.kotlinrest.util.converters.AccountTypeConverter
import pl.komorowskidev.kotlinrest.util.converters.CustomerConverter
import pl.komorowskidev.kotlinrest.util.converters.TransactionConverter
import java.io.IOException

@Component
class InitApplication(
    dbService: DbService,
    private val dataFilePathName: DataFilePathName,
    private val accountTypeConverter: AccountTypeConverter,
    private val customerConverter: CustomerConverter,
    private val transactionConverter: TransactionConverter,
    private val fileLoader: FileLoader,
    private val accountTypeService: AccountTypeService,
    private val customerService: CustomerService,
    private val transactionService: TransactionService) {

    companion object {
        private val logger = LogManager.getLogger()
    }

    init {
        dbService.clearDataBase()
        try {
            loadExampleData()
        } catch(e: IOException) {
            logger.error("File not found. {}", e.message)
        }
        dbService.removeTemporaryCollection("account-type")
        dbService.removeTemporaryCollection("customer")
    }

    @Throws(IOException::class)
    private fun loadExampleData(){
        fileLoader.load(
                dataFilePathName.accountTypes,
                accountTypeConverter,
                accountTypeService)
        fileLoader.load(
                dataFilePathName.customers,
                customerConverter,
                customerService)
        fileLoader.load(
                dataFilePathName.transactions,
                transactionConverter,
                transactionService)
    }

}
