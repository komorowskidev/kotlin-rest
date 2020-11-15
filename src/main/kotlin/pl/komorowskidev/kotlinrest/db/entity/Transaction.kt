package pl.komorowskidev.kotlinrest.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
class Transaction (
        @Id
        val id: Long,
        val amountInCents: Int,
        val accountTypeId: Long,
        val customerId: Long,
        val date: LocalDateTime
)