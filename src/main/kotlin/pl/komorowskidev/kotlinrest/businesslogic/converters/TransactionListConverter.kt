package pl.komorowskidev.kotlinrest.businesslogic.converters

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.businesslogic.services.AccountTypeService
import pl.komorowskidev.kotlinrest.businesslogic.services.CustomerService
import pl.komorowskidev.kotlinrest.db.dao.AccountTypeDao
import pl.komorowskidev.kotlinrest.db.dao.CustomerDao
import pl.komorowskidev.kotlinrest.db.dao.TransactionDao
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import pl.komorowskidev.kotlinrest.util.IdsExtractor

@Component
class TransactionListConverter(
        private val accountTypeService: AccountTypeService,
        private val customerService: CustomerService,
        private val idsExtractor: IdsExtractor,
        private val transactionConverter: TransactionConverter) {

    fun toTransactionDto(transactionDaoList: List<TransactionDao>): List<TransactionDto> {
        val transactionDtoList = ArrayList<TransactionDto>()
        val idSets = idsExtractor.getIdSets(transactionDaoList)
        val accountTypeMap = accountTypeService.getAccountTypeMap(idSets.accountTypeIdSet)
        val customerMap = customerService.getCustomerMap(idSets.customerIdSet)
        val iterator = transactionDaoList.iterator()
        while(iterator.hasNext()){
            addToTransactionDtoList(transactionDtoList, iterator.next(), accountTypeMap, customerMap)
        }
        return transactionDtoList
    }

    private fun addToTransactionDtoList(
            transactionDtoList: ArrayList<TransactionDto>,
            transactionDao: TransactionDao,
            accountTypeDaoMap: Map<Long, AccountTypeDao>,
            customerDaoMap: Map<Long, CustomerDao>) {
        val accountType = accountTypeDaoMap[transactionDao.accountTypeId]
        val customer = customerDaoMap[transactionDao.customerId]
        if(accountType != null && customer != null){
            transactionDtoList.add(transactionConverter.toTransactionDto(
                    transactionDao,
                    accountType.name,
                    customer.firstName,
                    customer.lastName))
        }
    }

}
