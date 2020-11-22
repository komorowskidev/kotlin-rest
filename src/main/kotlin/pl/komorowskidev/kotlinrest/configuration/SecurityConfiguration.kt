package pl.komorowskidev.kotlinrest.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
open class SecurityConfiguration(private val userDetailsManagerCreator: UserDetailsManagerCreator) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun userDetailsService(): UserDetailsService {
        return userDetailsManagerCreator.getUserDetailsManager()
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
