package de.winkler.splitthebills.controller.ui.vaadin.views.dialog

import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import de.winkler.splitthebills.entity.Bill
import de.winkler.splitthebills.entity.BillBuilder

class BillPartView(val billBuilder: BillBuilder) : VerticalLayout() {

    init {
        isEnabled = billBuilder.totalAmount != 0
        var parts = billBuilder.bill?.billParts?.map {
            HorizontalLayout(
                Span(it.person.name),
                Span(it.amount.toString()),
                Span(String.format("%.3f%%", it.part() * 100))
            )


        }

        if (parts != null) {

            add(VerticalLayout(*parts.toTypedArray()))
        }
    }

    open fun billChange() {
        isEnabled = billBuilder.totalAmount != 0
    }
}