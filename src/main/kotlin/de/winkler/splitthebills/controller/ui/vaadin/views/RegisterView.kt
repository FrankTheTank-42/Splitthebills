package de.winkler.splitthebills.controller.ui.vaadin.views

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.VaadinRequest
import com.vaadin.flow.server.auth.AnonymousAllowed
import de.winkler.splitthebills.entity.NewAccount
import de.winkler.splitthebills.service.repository.AccountRepository
import org.springframework.security.crypto.password.PasswordEncoder


@PageTitle("Register")
@Route("register")
@AnonymousAllowed
class RegisterView(
    val accountRepository: AccountRepository,
    val encoder: PasswordEncoder,
    val request: VaadinRequest
) : VerticalLayout() {
    init {
        val username = TextField("Username")
        val password = TextField("password")
        val email = TextField("email")
        val register = Button("register")
        register.addClickListener {
            ui.get().access {
                var newAccount = NewAccount(username.value,email.value,password.value)
                if (!newAccount.isComplete()) {
                    Notification.show("Please Add everything")
                } else if (accountRepository.findByName(newAccount.name!!).isPresent) {
                    Notification.show("The Name " + newAccount.name!! + " already exists.")
                } else {
                    println( request.contextPath)
                    accountRepository.save(newAccount.toAccount(encoder))
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