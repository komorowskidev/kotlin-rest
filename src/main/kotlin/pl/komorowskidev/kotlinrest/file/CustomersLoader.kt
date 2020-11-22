package pl.komorowskidev.kotlinrest.file

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.dao.CustomerDao
import pl.komorowskidev.kotlinrest.properties.ColumnName
import pl.komorowskidev.kotlinrest.properties.DataFilePathName

@Component
class CustomersLoader(
    private val dataFilePathName: DataFilePathName,
    private val columnName: ColumnName,
    private val fileHelper: FileHelper) {

    companion object {
        private val logger = LogManager.getLogger()
    }

    fun getCustomers(): List<CustomerDao> {
        return getCustomer(fileHelper.getLines(dataFilePathName.customers))
    }

    private fun getCustomer(lines: List<String>): List<CustomerDao> {
        val result = ArrayList<CustomerDao>()
        if(lines.isNotEmpty()) {
            iterateCustomers(result, lines)
        }
        return result
    }

    private fun iterateCustomers(result: ArrayList<CustomerDao>, lines: List<String>) {
        val iterator = lines.iterator()
        val line = iterator.next()
        if (line == columnName.customers){
            while(iterator.hasNext()) {
                addCustomer(result, iterator.next())
            }
        }
    }

    private fun addCustomer(result: ArrayList<CustomerDao>, line: String) {
        val record = line.split(",")
        if(record.size == 5){
            try {
                var balance = record[3].replace("\"", "").toInt()
                balance = balance * 100 + record[4].replace("\"", "").toInt()
                result.add(CustomerDao(record[0].toLong(), record[1], record[2], balance))
            } catch(e: NumberFormatException) {
                logger.error("id or balance error at {}", line)
            }
        }
    }
}
