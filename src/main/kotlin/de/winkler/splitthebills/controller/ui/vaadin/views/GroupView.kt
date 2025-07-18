package de.winkler.splitthebills.controller.ui.vaadin.views

import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.BeforeEvent
import com.vaadin.flow.router.HasUrlParameter
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.security.AuthenticationContext
import de.winkler.splitthebills.entity.Account
import de.winkler.splitthebills.entity.Bill
import de.winkler.splitthebills.entity.Group
import de.winkler.splitthebills.entity.Person
import de.winkler.splitthebills.service.BillService
import jakarta.annotation.security.PermitAll

@PageTitle("Group")
@Route("group")
@PermitAll
class GroupView(val authContent: AuthenticationContext, val billService: BillService) :
    HasUrlParameter<String>, VerticalLayout() {
    val billGrid: Grid<Bill>
    val personGrid: Grid<Person>
    val accountGrid: Grid<Account>
    val title: H1
    var group: Group? = null

    init {

        billGrid = Grid(Bill::class.java)
        personGrid = Grid(Person::class.java)
        accountGrid = Grid(Account::class.java)
        title = H1("Group")
        var newPersonTextField = TextField()
        newPersonTextField.placeholder = "new Person"
        var addPersonButton = Button("Add a Person")
        addPersonButton.addClickListener { click ->
            ui.get().access {
                if (group != null) {
                    var person = Person(newPersonTextField.value)
                    group!!.persons.add(person)
                    billService.saveGroup(group!!)
                    personGrid.setItems(group!!.persons)
                }

            }
        }
        addPersonButton.addClickShortcut(Key.ENTER)

        add(
            title,
            H2("Bills"),
            billGrid,
            H2("Persons"),

            personGrid,
            HorizontalLayout(newPersonTextField, addPersonButton),
            H2("Accounts"),
            accountGrid
        )
    }

    override fun setParameter(event: BeforeEvent?, groupid: String?) {
        if (groupid.isNullOrEmpty()) {
            Notification.show("Group id is empty")
        } else {
            val group = billService.findGroupById(authContent.principalName.get(), groupid)
            println(group)
            if (group == null) {
                Notification.show("Group not Found")
            } else {
                this.group = group
                title.text = group.name
                personGrid.setItems(group.persons)
                accountGrid.setItems(group.accounts)
                billGrid.setItems(group.entries)
            }
        }
    }

}