package de.winkler.splitthebills.controller.ui.vaadin.views

import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Menu
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.security.AuthenticationContext
import de.winkler.splitthebills.entity.Group
import de.winkler.splitthebills.service.BillService
import jakarta.annotation.security.PermitAll
import jakarta.annotation.security.RolesAllowed
import org.vaadin.lineawesome.LineAwesomeIconUrl


@PageTitle("Groups")
@Route("groups")
@Menu(order = .0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
@PermitAll
@RolesAllowed("USER")
class GroupsView(val authContent: AuthenticationContext, val billService: BillService) : VerticalLayout() {
    init {
        val newGroupTextfield = TextField()
        val addButton = Button("Add")

        val groupsList = billService.listGroups(authContent.principalName.get())
        val groupsListLayout = VerticalLayout()
        groupsList.forEach { group ->
            run {
                add(group, groupsListLayout)
            }
        }


        addButton.addClickListener { click ->
            ui.get().access {
                var g = billService.newGroup(
                    newGroupTextfield.getValue(),
                    authContent.principalName.get()
                )
                if (g == null) {
                    Notification.show("You have already created a group with that name")
                } else {
                    groupsList.add(g)
                    add(g, groupsListLayout)
                }
            }
        }

        addButton.addClickShortcut(Key.ENTER)


        add(
            H1("Groups"),
            groupsListLayout,
            HorizontalLayout(newGroupTextfield, addButton)
        )
    }

    private fun add(
        group: Group,
        groupsListLayout: VerticalLayout
    ) {
        var b = Button(group.name)
        b.addClickListener {
            b.ui.get().navigate(GroupView::class.java, group.id.toString())
        }
        var delete = Button(VaadinIcon.TRASH.create())
        var layout = HorizontalLayout(b, delete)
        delete.addClickListener { click ->
            delete.ui.get().access {
                billService.deleteGroup(group.id!!)
                groupsListLayout.remove(layout)
            }
        }
        groupsListLayout.addAndExpand(layout)
    }

}