package pl.komorowskidev.kotlinrest.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import pl.komorowskidev.kotlinrest.db.dao.AccountTypeDao

interface AccountTypeRepository : MongoRepository<AccountTypeDao, Long> {
}
