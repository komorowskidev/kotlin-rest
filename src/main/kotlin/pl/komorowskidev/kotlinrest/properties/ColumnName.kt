package pl.komorowskidev.kotlinrest.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "columnname")
class ColumnName (
    var customers: String = "",
    var accountTypes: String = "",
    var transactions: String = ""
)
