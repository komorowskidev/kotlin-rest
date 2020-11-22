package pl.komorowskidev.kotlinrest.db.dao

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class AccountTypeDao (
    @Id
    val id: Long,
    val name: String
)
