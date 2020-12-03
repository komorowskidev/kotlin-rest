package pl.komorowskidev.kotlinrest.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import pl.komorowskidev.kotlinrest.db.entities.AccountTypeEntity

interface AccountTypeRepository: MongoRepository<AccountTypeEntity, Long>
