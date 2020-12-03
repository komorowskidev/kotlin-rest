package pl.komorowskidev.kotlinrest.db.services

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entities.CustomerEntity
import pl.komorowskidev.kotlinrest.db.entities.Entity
import pl.komorowskidev.kotlinrest.db.repository.CustomerRepository
import java.util.*

@Component
class CustomerService(
    private val repository: CustomerRepository): Service {

    override fun save(entity: Entity){
        repository.save(entity as CustomerEntity)
    }

    fun findById(id: Long): Optional<CustomerEntity> {
        return repository.findById(id)
    }

}
