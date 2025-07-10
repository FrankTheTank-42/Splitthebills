package de.winkler.splitthebills.controller.ui.vaadin.views.welcome

import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Menu
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import com.vaadin.flow.theme.lumo.LumoUtility.Margin
import jakarta.annotation.security.PermitAll
import org.vaadin.lineawesome.LineAwesomeIconUrl

@PageTitle("Welcome")
@Route("")
@Menu(order = 0.0, icon = LineAwesomeIconUrl.FILE)
@AnonymousAllowed
class WelcomeView : VerticalLayout() {
    init {
        isSpacing = false
        val header = H1("Welcome to Split the bills")
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM)
        add(header)

        setSizeFull()
        justifyContentMode = JustifyContentMode.CENTER
        defaultHorizontalComponentAlignment = FlexComponent.Alignment.CENTER
        style["text-align"] = "center"
    }
}