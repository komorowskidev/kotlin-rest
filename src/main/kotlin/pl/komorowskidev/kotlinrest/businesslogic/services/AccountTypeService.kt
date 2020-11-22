package pl.komorowskidev.kotlinrest.businesslogic.services

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.dao.AccountTypeDao
import pl.komorowskidev.kotlinrest.db.repository.AccountTypeRepository

@Component
class AccountTypeService(
    private val accountTypeRepository: AccountTypeRepository){

    companion object {
        private val logger = LogManager.getLogger()
    }

    fun getAccountTypeMap(accountTypeIdSet: Set<Long>): Map<Long, AccountTypeDao> {
        val result = HashMap<Long, AccountTypeDao>()
        try {
            accountTypeRepository.findAllById(accountTypeIdSet).forEach { addToMap(result, it) }
        } catch(e: IllegalArgumentException){
            logger.warn("accountTypeId is null")
        }
        return result
    }

    private fun addToMap(result: HashMap<Long, AccountTypeDao>, accountTypeDao: AccountTypeDao) {
        result[accountTypeDao.id] = accountTypeDao
    }

}
