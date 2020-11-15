package pl.komorowskidev.kotlinrest.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import pl.komorowskidev.kotlinrest.db.entity.AccountType

interface AccountTypeRepository : MongoRepository<AccountType, Long> {
}