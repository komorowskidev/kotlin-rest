package pl.komorowskidev.kotlinrest.db.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "account-type")
class AccountTypeEntity (
    @Id
    val id: Long,
    val name: String
): Entity
