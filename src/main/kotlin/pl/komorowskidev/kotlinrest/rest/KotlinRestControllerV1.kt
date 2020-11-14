package pl.komorowskidev.kotlinrest.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class KotlinRestControllerV1 {

    @GetMapping("/transaction")
    fun transaction(
        @RequestParam(value = "account_type", defaultValue = "") accountType: String,
        @RequestParam(value = "customer_id", defaultValue = "") customerId: String) = "test $accountType $customerId"
}