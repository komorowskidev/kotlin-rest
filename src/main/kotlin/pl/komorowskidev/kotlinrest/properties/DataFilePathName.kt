package pl.komorowskidev.kotlinrest.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "datafilepathname")
class DataFilePathName(
    var accountTypes: String = "",
    var customers: String = "",
    var transactions: String = ""
)
