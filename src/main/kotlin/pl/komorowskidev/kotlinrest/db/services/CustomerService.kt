package pl.komorowskidev.kotlinrest.db.services

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entity.Customer
import pl.komorowskidev.kotlinrest.db.repository.CustomerRepository

@Component
class CustomerService(
    private val repository: CustomerRepository){

    fun getCustomerMap(idSet: Set<Long>): Map<Long, Customer> {
        val result = HashMap<Long, Customer>()
        repository.findAllById(idSet).forEach { addToMap(result, it) }
        return result
    }

    private fun addToMap(result: HashMap<Long, Customer>, customer: Customer) {
        result.put(customer.id, customer)
    }

}