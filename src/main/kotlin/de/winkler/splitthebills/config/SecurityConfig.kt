package de.winkler.splitthebills.config

import com.vaadin.flow.spring.security.VaadinWebSecurity
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig : VaadinWebSecurity() {

    override fun configure(http: HttpSecurity?) {

        if (http != null) {
            http {
                authorizeHttpRequests {
                    authorize("/css/**", permitAll)
                    authorize("/default-ui.css", permitAll)
                    authorize("/ui/user/new", permitAll)
                    authorize("/api/account/create", permitAll)
                    authorize("/login", permitAll)
                    authorize("/ui/user/login", permitAll)
                    //authorize("/ui/vaadin/account/login", permitAll)
                    //authorize("/VAADIN/**", permitAll)
                    authorize("/**", hasAuthority("USER"))
                }



                formLogin {
                }
            }
        }
        super.configure(http)


    }


    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder(11)
    }
}
