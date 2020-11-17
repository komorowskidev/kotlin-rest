package pl.komorowskidev.kotlinrest.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.komorowskidev.kotlinrest.rest.dto.TransactionDto
import pl.komorowskidev.kotlinrest.db.services.TransactionService

@RestController
@RequestMapping("/api/v1")
class KotlinRestControllerV1(
    private val transactionService: TransactionService
) {

    @GetMapping("/transaction")
    fun transaction(
        @RequestParam(value = "account_type", defaultValue = "all") accountTypeId: String,
        @RequestParam(value = "customer_id", defaultValue = "all") customerId: String): List<TransactionDto> {
        return transactionService.getTransactions(accountTypeId, customerId)
    }
}