package de.winkler.splitthebills.controller.ui.vaadin.views.demo.about

import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Menu
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.lumo.LumoUtility.Margin
import jakarta.annotation.security.PermitAll
import org.vaadin.lineawesome.LineAwesomeIconUrl

@PageTitle("About")
@Route("about")
@Menu(order = 1.0, icon = LineAwesomeIconUrl.FILE)
@PermitAll
class AboutView : VerticalLayout() {
    init {
        isSpacing = false

        val img = Image("images/empty-plant.png", "placeholder plant")
        img.width = "200px"
        add(img)

        val header = H2("This place intentionally left empty")
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM)
        add(header)
        add(Paragraph("It’s a place where you can grow your own UI 🤗"))

        setSizeFull()
        justifyContentMode = JustifyContentMode.CENTER
        defaultHorizontalComponentAlignment = FlexComponent.Alignment.CENTER
        style["text-align"] = "center"
    }
}