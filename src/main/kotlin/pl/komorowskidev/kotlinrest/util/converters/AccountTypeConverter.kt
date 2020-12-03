package pl.komorowskidev.kotlinrest.util.converters

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entities.AccountTypeEntity

@Component
class AccountTypeConverter: Converter {

    companion object {
        private val logger = LogManager.getLogger()
    }

    override fun dtoToEntity(line: String): AccountTypeEntity? {
        var result: AccountTypeEntity? = null
        val record = line.split(",")
        if(record.size == 2) {
            try {
                result = AccountTypeEntity(record[0].toLong(), record[1])
            } catch(e: NumberFormatException) {
                logger.error("AccountType - convert ID to Long error at line: {}", line)
            }
        } else {
            logger.error("Wrong number of column at line: {}", line)
        }
        return result
    }
}