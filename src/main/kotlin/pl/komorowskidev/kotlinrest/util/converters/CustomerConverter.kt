package pl.komorowskidev.kotlinrest.util.converters

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entities.CustomerEntity

@Component
class CustomerConverter: Converter {

    companion object {
        private val logger = LogManager.getLogger()
    }

    override fun dtoToEntity(line: String): CustomerEntity? {
        var result: CustomerEntity? = null
        val record = line.split(",")
        if(record.size == 5){
            try {
                val id = record[0].toLong()
                var balance: Int = record[3].replace("\"", "").toInt()
                balance = balance * 100 + record[4].replace("\"", "").toInt()
                result = CustomerEntity(id, record[1], record[2], balance)
            } catch(e: NumberFormatException) {
                logger.error("Customer data - id or balance error at line: {}", line)
            }
        } else {
            logger.error("Wrong number of column at line: {}", line)
        }
        return result
    }
}