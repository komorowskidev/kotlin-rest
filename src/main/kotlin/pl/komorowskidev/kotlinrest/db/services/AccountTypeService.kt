package pl.komorowskidev.kotlinrest.db.services

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entities.AccountTypeEntity
import pl.komorowskidev.kotlinrest.db.entities.Entity
import pl.komorowskidev.kotlinrest.db.repository.AccountTypeRepository
import java.util.*

@Component
class AccountTypeService(
    private val repository: AccountTypeRepository): Service {

    override fun save(entity: Entity){
        repository.save(entity as AccountTypeEntity)
    }

    fun findById(id: Long): Optional<AccountTypeEntity> {
        return repository.findById(id)
    }
}
