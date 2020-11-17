package pl.komorowskidev.kotlinrest.util

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.entity.AccountType
import pl.komorowskidev.kotlinrest.db.entity.Customer
import pl.komorowskidev.kotlinrest.db.entity.Transaction
import pl.komorowskidev.kotlinrest.db.services.AccountTypeService
import pl.komorowskidev.kotlinrest.db.services.CustomerService
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto

@Component
class TransactionListConverter(
        private val accountTypeService: AccountTypeService,
        private val customerService: CustomerService,
        private val idsExtractor: IdsExtractor,
        private val transactionConverter: TransactionConverter) {

    fun toTransactionDto(transactionList: List<Transaction>): List<TransactionDto> {
        val transactionDtoList = ArrayList<TransactionDto>()
        val idLists = idsExtractor.getIdSets(transactionList)
        val accountTypeMap = accountTypeService.getAccountTypeMap(idLists.accountTypeIdList)
        val customerMap = customerService.getCustomerMap(idLists.customerIdList)
        val iterator = transactionList.iterator()
        while(iterator.hasNext()){
            addToTransactionDtoList(transactionDtoList, iterator.next(), accountTypeMap, customerMap)
        }
        return transactionDtoList
    }

    private fun addToTransactionDtoList(
            transactionDtoList: ArrayList<TransactionDto>,
            transaction: Transaction,
            accountTypeMap: Map<Long, AccountType>,
            customerMap: Map<Long, Customer>) {
        val accountType = accountTypeMap.get(transaction.accountTypeId)
        val customer = customerMap.get(transaction.accountTypeId)
        if(accountType != null && customer != null){
            transactionDtoList.add(transactionConverter.toTransactionDto(
                    transaction,
                    accountType.name,
                    customer.firstName,
                    customer.lastName))
        }
    }

}