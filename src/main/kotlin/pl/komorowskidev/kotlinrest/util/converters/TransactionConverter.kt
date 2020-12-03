package pl.komorowskidev.kotlinrest.util.converters

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entities.AccountTypeEntity
import pl.komorowskidev.kotlinrest.db.entities.CustomerEntity
import pl.komorowskidev.kotlinrest.db.entities.TransactionEntity
import pl.komorowskidev.kotlinrest.db.services.AccountTypeService
import pl.komorowskidev.kotlinrest.db.services.CustomerService
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Component
class TransactionConverter(
        private val accountTypeService: AccountTypeService,
        private val customerService: CustomerService): Converter {

    private val oneHundred = BigDecimal(100)
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val formatterWithTimeZone = dateFormatter.withZone(ZoneId.from(UTC))

    companion object {
        private val logger = LogManager.getLogger()
    }

    override fun dtoToEntity(line: String): TransactionEntity? {
        var result: TransactionEntity? = null
        val record = line.split(",")
        if(record.size == 6){
            try {
                val id = record[0].toLong()
                val amount = record[1].replace("\"", "").toInt()
                val amountInCents = amount * 100 + record[2].replace("\"", "").toInt()
                val accountTypeEntity = getAccountTypeEntity(record[3].toLong())
                val customerEntity = getCustomerEntity(record[4].toLong())
                val utc = LocalDateTime.parse(record[5], dateFormatter).atZone(ZoneId.systemDefault()).toInstant()
                result = TransactionEntity(
                        id,
                        amountInCents,
                        accountTypeEntity.id,
                        accountTypeEntity.name,
                        customerEntity.id,
                        customerEntity.firstName,
                        customerEntity.lastName,
                        customerEntity.lastLoginBalanceInCents,
                        utc)
            } catch(e: NumberFormatException) {
                logger.error("error in numbers at line: {}", line)
            } catch(e: DateTimeParseException) {
                logger.error("cannot parse date at line: {}", line)
            }
        } else {
            logger.error("Wrong number of column at line: {}", line)
        }
        return result
    }

    private fun getAccountTypeEntity(id: Long): AccountTypeEntity {
        return accountTypeService
                .findById(id)
                .orElse(AccountTypeEntity(id, "unknown"))
    }

    private fun getCustomerEntity(id: Long): CustomerEntity {
        return customerService
                .findById(id)
                .orElse(CustomerEntity(id, "unknown", "unknown", 0))
    }

    fun entityToDto(transactionEntity: TransactionEntity): TransactionDto {
        val utc = formatterWithTimeZone.format(transactionEntity.utcDate)
        val amount = (transactionEntity.amountInCents.toBigDecimal().setScale(2).div(oneHundred)).toString()
        return TransactionDto(
                utc,
                transactionEntity.id,
                amount,
                transactionEntity.accountTypeName,
                transactionEntity.customerFirstName,
                transactionEntity.customerLastName
        )
    }
}
