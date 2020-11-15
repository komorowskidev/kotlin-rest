package pl.komorowskidev.kotlinrest.file

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entity.Customer
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

    fun getCustomers(): List<Customer> {
        return getCustomer(fileHelper.getLines(dataFilePathName.customers))
    }

    private fun getCustomer(lines: List<String>): List<Customer> {
        val result = ArrayList<Customer>()
        if(!lines.isEmpty()) {
            iterateCustomers(result, lines)
        }
        return result
    }

    private fun iterateCustomers(result: ArrayList<Customer>, lines: List<String>) {
        val iterator = lines.iterator()
        val line = iterator.next()
        if (line.equals(columnName.customers)){
            while(iterator.hasNext()) {
                addCustomer(result, iterator.next())
            }
        }
    }

    private fun addCustomer(result: ArrayList<Customer>, line: String) {
        val record = line.split(",")
        if(record.size == 5){
            try {
                var balance = record[3].replace("\"", "").toInt()
                balance = balance * 100 + record[4].replace("\"", "").toInt()
                result.add(Customer(record[0].toLong(), record[1], record[2], balance))
            } catch(e: NumberFormatException) {
                logger.error("id or balance error at {}", line)
            }
        }
    }
}
