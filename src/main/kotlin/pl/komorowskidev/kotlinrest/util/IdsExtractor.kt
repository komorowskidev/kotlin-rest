package pl.komorowskidev.kotlinrest.util

import org.springframework.stereotype.Component

@Component
class IdsExtractor {

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
