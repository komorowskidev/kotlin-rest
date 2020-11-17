package pl.komorowskidev.kotlinrest.db.services

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entity.AccountType
import pl.komorowskidev.kotlinrest.db.repository.AccountTypeRepository

@Component
class AccountTypeService(
    private val accountTypeRepository: AccountTypeRepository){

    fun getAccountTypeMap(accountTypeIdSet: Set<Long>): Map<Long, AccountType> {
        val result = HashMap<Long, AccountType>()
        accountTypeRepository.findAllById(accountTypeIdSet).forEach { addToMap(result, it) }
        return result;
    }

    private fun addToMap(result: HashMap<Long, AccountType>, accountType: AccountType) {
        result.put(accountType.id, accountType);
    }

}