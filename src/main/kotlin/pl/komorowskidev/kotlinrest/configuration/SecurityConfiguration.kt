package pl.komorowskidev.kotlinrest.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import pl.komorowskidev.kotlinrest.properties.Users

@Configuration
open class SecurityConfiguration(val users: Users) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun userDetailsService(): UserDetailsService {
        val manager = InMemoryUserDetailsManager()
        users.userMap.forEach{userName, password ->
            manager.createUser(User
                .withUsername(userName.toString())
                .password("{noop}" + password.toString())
                .roles("USER").build())
        }
        return manager
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests().anyRequest().authenticated()
            .and().httpBasic()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService())
    }
}