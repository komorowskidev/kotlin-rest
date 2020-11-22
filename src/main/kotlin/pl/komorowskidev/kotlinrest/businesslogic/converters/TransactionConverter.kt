package pl.komorowskidev.kotlinrest.businesslogic.converters

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.dao.TransactionDao
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter

@Component
class TransactionConverter {

    private val oneHundred = BigDecimal(100)
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.from(UTC))

    fun toTransactionDto(
            transactionDao: TransactionDao,
            accountTypeName: String,
            customerFirstName: String,
            customerLastName: String
    ): TransactionDto {
        val utc = formatter.format(transactionDao.date)
        val id = transactionDao.id
        val amount = (transactionDao.amountInCents.toBigDecimal().setScale(2).div(oneHundred)).toString()
        return TransactionDto(
                utc,
                id,
                amount,
                accountTypeName,
                customerFirstName,
                customerLastName
        )
    }
}
