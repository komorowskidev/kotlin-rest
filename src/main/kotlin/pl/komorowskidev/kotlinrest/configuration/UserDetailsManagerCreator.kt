package pl.komorowskidev.kotlinrest.configuration

import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.properties.Users

@Component
class UserDetailsManagerCreator(private val users: Users) {

    fun getUserDetailsManager(): InMemoryUserDetailsManager {
        val manager = InMemoryUserDetailsManager()
        users.userMap.forEach { userName, password ->
            manager.createUser(User
                    .withUsername(userName.toString())
                    .password("{noop}$password")
                    .roles("USER").build())
        }
        return manager
    }
}
