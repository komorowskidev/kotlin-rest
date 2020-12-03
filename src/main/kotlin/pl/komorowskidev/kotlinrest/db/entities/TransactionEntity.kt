package pl.komorowskidev.kotlinrest.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "transaction")
class TransactionEntity(
        @Id
        val id: Long,
        val amountInCents: Int,
        val accountTypeId: Long,
        val accountTypeName: String,
        val customerId: Long,
        val customerFirstName: String,
        val customerLastName: String,
        val customerLastLoginBalanceInCents: Int,
        val utcDate: Instant
): Entity
