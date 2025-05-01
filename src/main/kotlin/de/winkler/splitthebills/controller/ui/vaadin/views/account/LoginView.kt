package de.winkler.splitthebills.controller.ui.vaadin.views.account

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.PasswordField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import jakarta.annotation.security.PermitAll
import jdk.jfr.ContentType
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers


@PageTitle("Login")
@Route("ui/vaadin/account/login")
@AnonymousAllowed
class LoginView  : VerticalLayout() {
    init {
        val username = TextField("Username")
        val password = PasswordField("Password")
        val confirmPassword = PasswordField("Confirm password")

        val formLayout = FormLayout()
        formLayout.add(
            username, password,
            confirmPassword
        )
        formLayout.setResponsiveSteps( // Use one column by default
            ResponsiveStep("0", 1),  // Use two columns, if layout's width exceeds 500px
            ResponsiveStep("500px", 2)
        )

// Stretch the username field over 2 columns
        formLayout.setColspan(username, 2)


        val loginButton: Button = Button("Login")
        loginButton.addClickListener {
            //makeLogin(username.value, password.value);

        }

        add(
            H1("login"),
            formLayout,
            loginButton
        )
    }

    fun makeLogin(username: String, password: String) {
        val client = HttpClient.newHttpClient();
        val payload = "username=$username&password=$password"
        val request = HttpRequest
            .newBuilder(URI("localhost:8080/login"))
            .setHeader("ContentType", "application/x-www-form-urlencoded")
            .POST(BodyPublishers.ofString(payload)).build();
        val response = client.send(request, BodyHandlers.ofString());
        if (response.statusCode() == 302 && response.headers().firstValue("location").isPresent) {
            UI.getCurrent().navigate(response.headers().firstValue("location").get())
        }

    }
}