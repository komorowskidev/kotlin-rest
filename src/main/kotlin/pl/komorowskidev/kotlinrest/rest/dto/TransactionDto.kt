package pl.komorowskidev.kotlinrest.rest.dto

import com.fasterxml.jackson.annotation.JsonProperty

class TransactionDto(
    @JsonProperty("Data transakcji") val utc: String,
    @JsonProperty("Identyfikator transakcji") val id: Long,
    @JsonProperty("Kwota transakcji") val amount: String,
    @JsonProperty("rodzaj rachunku") val accountType: String,
    @JsonProperty("Imię zlecającego") val firstName: String,
    @JsonProperty("Nazwisko Zlecającego") val lastName: String
)
