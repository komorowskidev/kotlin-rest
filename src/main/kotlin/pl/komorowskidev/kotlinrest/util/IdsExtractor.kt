package pl.komorowskidev.kotlinrest.util

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.beans.IdSets
import pl.komorowskidev.kotlinrest.db.dao.TransactionDao

@Component
class IdsExtractor {

    fun getIdSets(transactionDaoList: List<TransactionDao>): IdSets {
        val accountTypeIdSet = HashSet<Long>()
        val customerIdSet = HashSet<Long>()
        transactionDaoList.forEach {
            accountTypeIdSet.add(it.accountTypeId)
            customerIdSet.add(it.customerId)
        }
        return IdSets(accountTypeIdSet, customerIdSet)
    }

    fun getIdSet(ids: String): Set<Long> {
        val result = HashSet<Long>()
        if(ids.contains(",")){
            val idList = ids.split(",")
            idList.forEach{
                addToSet(result, it)
            }
        } else {
            addToSet(result, ids)
        }
        return result
    }

    private fun addToSet(result: HashSet<Long>, it: String) {
        try {
            result.add(it.toLong())
        } catch(e: NumberFormatException){}
    }

}
