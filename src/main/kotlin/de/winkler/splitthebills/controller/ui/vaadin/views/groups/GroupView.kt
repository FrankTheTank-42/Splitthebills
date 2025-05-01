package de.winkler.splitthebills.controller.ui.vaadin.views.groups

import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.*
import com.vaadin.flow.spring.security.AuthenticationContext
import de.winkler.splitthebills.entity.Account
import de.winkler.splitthebills.entity.Bill
import de.winkler.splitthebills.entity.Group
import de.winkler.splitthebills.entity.Person
import de.winkler.splitthebills.service.BillService
import jakarta.annotation.security.PermitAll

@PageTitle("Group")
@Route("ui/vaadin/group")
@PermitAll
class GroupView(val authContent: AuthenticationContext, val billService: BillService) :
    HasUrlParameter<String>, VerticalLayout() {
    val billGrid: Grid<Bill>;
    val personGrid: Grid<Person>;
    val accountGrid: Grid<Account>;
    val title: H1

    init {

        billGrid = Grid<Bill>(Bill::class.java)
        personGrid = Grid<Person>(Person::class.java)
        accountGrid = Grid<Account>(Account::class.java)
        title = H1("Group")


        add(
            title,
            H2("Bills"),
            billGrid,
            H2("Persons"),
            personGrid,
            H2("Accounts"),
            accountGrid
        )
    }

    override fun setParameter(event: BeforeEvent?, groupid: String?) {
        if (groupid.isNullOrEmpty()) {
            Notification.show("Group id is empty")
        } else {
            val group =
                billService.listGroups(authContent.principalName.get()).filter { g -> g.id.toString().equals(groupid) }
                    .firstOrNull();

            if (group == null) {
                Notification.show("Group not Found");
            } else {


                title.text = group.name;
                personGrid.setItems(group.persons)
                accountGrid.setItems(group.accounts)
                billGrid.setItems(group.entries)
            }
        }
    }

}