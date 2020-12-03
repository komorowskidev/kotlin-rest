package pl.komorowskidev.kotlinrest.rest.dto

import com.fasterxml.jackson.annotation.JsonProperty

class TransactionDto(
    @JsonProperty("date_utc") val utc: String,
    @JsonProperty("id") val id: Long,
    @JsonProperty("amount") val amount: String,
    @JsonProperty("account_type") val accountType: String,
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String
)
