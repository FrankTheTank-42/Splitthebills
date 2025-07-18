package de.winkler.splitthebills.controller.ui.vaadin.views

import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.login.LoginForm
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed


@PageTitle("Login")
@Route("login")
@AnonymousAllowed
class LoginView  : VerticalLayout() {
    init {
        val form= LoginForm()
        form.action = "/login"
        add(
            H1("login"),
            form
        )
    }

}