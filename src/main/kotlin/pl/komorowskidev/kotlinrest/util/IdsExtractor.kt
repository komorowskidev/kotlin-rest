package pl.komorowskidev.kotlinrest.util

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.beans.IdSets
import pl.komorowskidev.kotlinrest.db.entity.Transaction

@Component
class IdsExtractor {

    fun getIdSets(transactionList: List<Transaction>): IdSets {
        val accountTypeIdSet = HashSet<Long>()
        val customerIdSet = HashSet<Long>()
        transactionList.forEach {
            accountTypeIdSet.add(it.accountTypeId)
            customerIdSet.add(it.customerId)
        }
        return IdSets(accountTypeIdSet, customerIdSet)
    }

    fun getIdSet(customerId: String): Set<Long> {
        val result = HashSet<Long>()
        if(customerId.contains(",")){
            val idList = customerId.split(",")
            idList.forEach{
                addToSet(result, it)
            }
        } else {
            addToSet(result, customerId)
        }
        return result
    }

    private fun addToSet(result: HashSet<Long>, it: String) {
        try {
            result.add(it.toLong())
        } catch(e: NumberFormatException){}
    }

}