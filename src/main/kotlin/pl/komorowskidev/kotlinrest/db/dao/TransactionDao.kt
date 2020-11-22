package pl.komorowskidev.kotlinrest.db.dao

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
class TransactionDao (
        @Id
        val id: Long,
        val amountInCents: Int,
        val accountTypeId: Long,
        val customerId: Long,
        val date: Instant
)
