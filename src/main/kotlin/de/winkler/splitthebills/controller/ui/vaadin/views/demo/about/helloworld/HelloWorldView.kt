package de.winkler.splitthebills.controller.ui.vaadin.views.demo.about.helloworld

import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Menu
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import jakarta.annotation.security.PermitAll
import org.vaadin.lineawesome.LineAwesomeIconUrl

@PageTitle("Hello World")
@Route("")
@Menu(order = 0.0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
@PermitAll
class HelloWorldView : HorizontalLayout() {
    private val name = TextField("Your name")
    private val sayHello = Button("Say hello")

    init {
        sayHello.addClickListener { e: ClickEvent<Button?>? ->
            Notification.show("Hello " + name.value)
        }
        sayHello.addClickShortcut(Key.ENTER)

        isMargin = true
        setVerticalComponentAlignment(FlexComponent.Alignment.END, name, sayHello)

        add(name, sayHello)
    }
}