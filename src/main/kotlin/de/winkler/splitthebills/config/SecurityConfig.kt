package de.winkler.splitthebills.config

import com.vaadin.flow.spring.security.VaadinWebSecurity
import de.winkler.splitthebills.controller.ui.vaadin.views.LoginView
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig : VaadinWebSecurity() {

    override fun configure(http: HttpSecurity?) {
        http!!{
            authorizeHttpRequests {
                authorize("/line-awesome/**", permitAll)
            }
        }


        super.configure(http)
        setLoginView(http, LoginView::class.java)

    }


    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder(11)
    }
}
