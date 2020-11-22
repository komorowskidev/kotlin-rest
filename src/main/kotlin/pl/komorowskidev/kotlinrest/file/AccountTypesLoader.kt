package pl.komorowskidev.kotlinrest.file

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.dao.AccountTypeDao
import pl.komorowskidev.kotlinrest.properties.ColumnName
import pl.komorowskidev.kotlinrest.properties.DataFilePathName

@Component
class AccountTypesLoader(
    private val dataFilePathName: DataFilePathName,
    private val columnName: ColumnName,
    private val fileHelper: FileHelper) {

    companion object {
        private val logger = LogManager.getLogger()
    }

    fun getAccountTypes(): List<AccountTypeDao> {
        return getAccountTypeList(fileHelper.getLines(dataFilePathName.accountTypes))
    }

    private fun getAccountTypeList(lines: List<String>): List<AccountTypeDao> {
        val result = ArrayList<AccountTypeDao>()
        if(lines.isNotEmpty()) {
            iterateAccountTypes(result, lines)
        }
        return result
    }

    private fun iterateAccountTypes(result: ArrayList<AccountTypeDao>, lines: List<String>) {
        val iterator = lines.iterator()
        val line = iterator.next()
        if (line == columnName.accountTypes){
            while(iterator.hasNext()) {
                addAccountType(result, iterator.next())
            }
        }
    }

    private fun addAccountType(result: ArrayList<AccountTypeDao>, line: String) {
        val record = line.split(",")
        if(record.size == 2){
            try {
                result.add(AccountTypeDao(record[0].toLong(), record[1]))
            } catch(e: NumberFormatException) {
                logger.error("id error at {}", line)
            }
        }
    }

}
