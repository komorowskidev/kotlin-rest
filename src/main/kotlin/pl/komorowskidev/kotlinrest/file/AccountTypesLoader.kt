package pl.komorowskidev.kotlinrest.file

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entity.AccountType
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

    fun getAccountTypes(): List<AccountType> {
        return getAccountTypeList(fileHelper.getLines(dataFilePathName.accountTypes))
    }

    private fun getAccountTypeList(lines: List<String>): List<AccountType> {
        val result = ArrayList<AccountType>()
        if(!lines.isEmpty()) {
            iterateAccountTypes(result, lines)
        }
        return result
    }

    private fun iterateAccountTypes(result: ArrayList<AccountType>, lines: List<String>) {
        val iterator = lines.iterator()
        val line = iterator.next()
        if (line.equals(columnName.accountTypes)){
            while(iterator.hasNext()) {
                addAccountType(result, iterator.next())
            }
        }
    }

    private fun addAccountType(result: ArrayList<AccountType>, line: String) {
        val record = line.split(",")
        if(record.size == 2){
            try {
                result.add(AccountType(record[0].toLong(), record[1]))
            } catch(e: NumberFormatException) {
                logger.error("id error at {}", line)
            }
        }
    }
}
