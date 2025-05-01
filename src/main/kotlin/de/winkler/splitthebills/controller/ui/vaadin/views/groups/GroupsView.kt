package de.winkler.splitthebills.controller.ui.vaadin.views.groups

import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.renderer.ComponentRenderer
import com.vaadin.flow.data.renderer.NativeButtonRenderer
import com.vaadin.flow.router.Menu
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouteConfiguration
import com.vaadin.flow.spring.security.AuthenticationContext
import de.winkler.splitthebills.entity.Group
import de.winkler.splitthebills.entity.Person
import de.winkler.splitthebills.service.BillService
import jakarta.annotation.security.PermitAll
import org.vaadin.lineawesome.LineAwesomeIconUrl


@PageTitle("Groups")
@Route("ui/vaadin/groups")
@Menu(order = .0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
@PermitAll
class GroupsView(val authContent: AuthenticationContext, val billService: BillService) : VerticalLayout() {
    init {


        val taskField: TextField = TextField()


        val addButton: Button = Button("Add")

        val groupsList = billService.listGroups(authContent.principalName.get());
        val groupsGrid = Grid<Group>(groupsList)
        groupsGrid.addColumn(Group::name).setHeader("Name")
        groupsGrid.addColumn(
            ComponentRenderer({group -> run {
                var b = Button(group.name)
                b.addClickListener {
                    b.ui.get().navigate(GroupView::class.java, group.id.toString())
                }
                return@run b;
            }})).setHeader("Group")

        addButton.addClickListener { click ->
            Notification.show("Add a group coming soon...")
            billService.saveGroup(Group(taskField.getValue()), authContent.principalName.get())
        }
        addButton.addClickShortcut(Key.ENTER)

        add(
            H1("Groups"),
            groupsGrid,
            HorizontalLayout(taskField, addButton)
        )
    }

}