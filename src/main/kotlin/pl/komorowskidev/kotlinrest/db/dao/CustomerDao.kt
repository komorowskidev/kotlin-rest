package pl.komorowskidev.kotlinrest.db.dao

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class CustomerDao(
    @Id
    val id: Long,
    val firstName: String,
    val lastName: String,
    val lastLoginBalanceInCents: Int
)
