package pl.komorowskidev.kotlinrest.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Customer(
    @Id
    val id: Long,
    val firstName: String,
    val lastName: String,
    val lastLoginBalanceInCents: Int
)