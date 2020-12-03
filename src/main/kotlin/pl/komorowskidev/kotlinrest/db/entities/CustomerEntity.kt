package pl.komorowskidev.kotlinrest.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "customer")
class CustomerEntity(
    @Id
    val id: Long,
    val firstName: String,
    val lastName: String,
    val lastLoginBalanceInCents: Int
): Entity
