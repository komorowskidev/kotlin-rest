package pl.komorowskidev.kotlinrest.properties

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.PropertiesLoaderUtils
import org.springframework.stereotype.Component
import java.util.*

@Component
class Users {

    var userMap: Properties = PropertiesLoaderUtils.loadProperties(ClassPathResource("users.properties"))

}
