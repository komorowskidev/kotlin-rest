package pl.komorowskidev.kotlinrest.util

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entity.Transaction
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import java.math.BigDecimal
import java.time.format.DateTimeFormatter




@Component
class TransactionConverter() {

    private val HUNDRED = BigDecimal(100)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun toTransactionDto(
        transaction: Transaction,
        accountTypeName: String,
        customerFirstName: String,
        customerLastName: String
    ): TransactionDto {
        val date = transaction.date.format(formatter)
        val id = transaction.id
        val amount = (transaction.amountInCents.toBigDecimal().setScale(2).div(HUNDRED)).toString()
        return TransactionDto(
            date,
            id,
            amount,
            accountTypeName,
            customerFirstName,
            customerLastName
        )
    }

}