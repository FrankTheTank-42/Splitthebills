package de.winkler.splitthebills.controller.ui.vaadin.views

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Menu
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import com.vaadin.flow.spring.security.AuthenticationContext
import com.vaadin.flow.theme.lumo.LumoUtility.Margin
import org.vaadin.lineawesome.LineAwesomeIconUrl

@PageTitle("Welcome")
@Route("")
@Menu(order = 0.0, icon = LineAwesomeIconUrl.FILE)
@AnonymousAllowed
class WelcomeView(authContext: AuthenticationContext) : VerticalLayout() {
    init {
        isSpacing = false
        val header = H1("Welcome to Split the bills")
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM)
        add(header)
        if(authContext.isAuthenticated){
            add(Paragraph("Welcome " + authContext.principalName.get()))
        } else {
            add("You are not logged in")
            val registerButton = Button("Register")
            registerButton.addClickListener { ui.get().page.setLocation("/register") }
            add(registerButton)
            val loginButton = Button("Log in")
            loginButton.addClickListener {
                ui.get().page.setLocation("/login")
            }
            add(loginButton)
        }

        setSizeFull()

        justifyContentMode = JustifyContentMode.CENTER
        defaultHorizontalComponentAlignment = FlexComponent.Alignment.CENTER
        style["text-align"] = "center"
    }
}