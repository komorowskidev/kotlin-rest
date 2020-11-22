package pl.komorowskidev.kotlinrest.businesslogic.services

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.dao.CustomerDao
import pl.komorowskidev.kotlinrest.db.repository.CustomerRepository

@Component
class CustomerService(
    private val repository: CustomerRepository){

    fun getCustomerMap(idSet: Set<Long>): Map<Long, CustomerDao> {
        val result = HashMap<Long, CustomerDao>()
        repository.findAllById(idSet).forEach { addToMap(result, it) }
        return result
    }

    private fun addToMap(result: HashMap<Long, CustomerDao>, customerDao: CustomerDao) {
        result[customerDao.id] = customerDao
    }

}
