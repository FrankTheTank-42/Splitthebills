package de.winkler.splitthebills.controller.ui.vaadin.views

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import de.winkler.splitthebills.entity.NewAccount
import de.winkler.splitthebills.service.repository.AccountRepository
import org.springframework.security.crypto.password.PasswordEncoder


@PageTitle("Register")
@Route("register")
@AnonymousAllowed
class RegisterView(
    val accountRepository: AccountRepository,
    val encoder: PasswordEncoder
) : VerticalLayout() {
    init {
        val username = TextField("Username")
        val password = TextField("password")
        val email = TextField("email")
        val register = Button("register")
        register.addClickListener {
            ui.get().access {
                var c = NewAccount(username.value, password.value, email.value)
                if (!c.isComplete()) {
                    Notification.show("Please Add everything")
                } else if (accountRepository.findByName(c.name!!).isPresent) {
                    Notification.show("The Name " + c.name!! + " already exists.")
                } else {
                    accountRepository.save(c.toAccount(encoder))
                    ui.get().page.setLocation("/login")
                }
            }
        }
        add(
            H1("Register"),
            username, password, email,
            register
        )

    }

}