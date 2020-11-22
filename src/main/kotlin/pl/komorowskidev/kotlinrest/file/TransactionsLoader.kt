package pl.komorowskidev.kotlinrest.file

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.dao.TransactionDao
import pl.komorowskidev.kotlinrest.properties.ColumnName
import pl.komorowskidev.kotlinrest.properties.DataFilePathName
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Component
class TransactionsLoader(
    private val dataFilePathName: DataFilePathName,
    private val columnName: ColumnName,
    private val fileHelper: FileHelper) {

    companion object {
        private val logger = LogManager.getLogger()
    }

    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun getTransactions(): List<TransactionDao> {
        return getTransactions(fileHelper.getLines(dataFilePathName.transactions))
    }

    private fun getTransactions(lines: List<String>): List<TransactionDao> {
        val result = ArrayList<TransactionDao>()
        if(lines.isNotEmpty()) {
            iterateTransactions(result, lines)
        }
        return result
    }

    private fun iterateTransactions(result: ArrayList<TransactionDao>, lines: List<String>) {
        val iterator = lines.iterator()
        val line = iterator.next()
        if (line == columnName.transactions){
            while(iterator.hasNext()) {
                addTransaction(result, iterator.next())
            }
        }
    }

    private fun addTransaction(result: ArrayList<TransactionDao>, line: String) {
        val record = line.split(",")
        if(record.size == 6){
            try {
                val id = record[0].toLong()
                var amount = record[1].replace("\"", "").toInt()
                amount = amount * 100 + record[2].replace("\"", "").toInt()
                val accountTypeId = record[3].toLong()
                val customerId = record[4].toLong()
                val utc = LocalDateTime.parse(record[5], dateFormatter).atZone(ZoneId.systemDefault()).toInstant()
                result.add(TransactionDao(id, amount, accountTypeId, customerId, utc))
            } catch(e: NumberFormatException) {
                logger.error("error in numbers at {}", line)
            } catch(e: DateTimeParseException) {
                logger.error("cannot parse date at {}", line)
            }
        }
    }
}
