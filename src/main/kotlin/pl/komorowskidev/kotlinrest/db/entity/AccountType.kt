package pl.komorowskidev.kotlinrest.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class AccountType (
    @Id
    val id: Long,
    val name: String
)