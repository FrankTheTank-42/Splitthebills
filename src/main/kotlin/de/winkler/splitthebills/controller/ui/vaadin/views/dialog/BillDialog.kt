package de.winkler.splitthebills.controller.ui.vaadin.views.dialog

import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import de.winkler.splitthebills.entity.Bill
import de.winkler.splitthebills.entity.BillBuilder
import de.winkler.splitthebills.entity.Person
import java.util.function.Consumer

class BillDialog(val billBuilder: BillBuilder) : Dialog() {

    constructor(persons: MutableList<Person>) : this(BillBuilder(persons))

    var billNameTextField: TextField =
        TextField("Name", "Name").also {
            if (billBuilder.name != null) {
                it.value = billBuilder.name
            }
        }
    var billTotalAmountTextField = TextField("Total Amount", "0.00").also {
        if (billBuilder.totalAmount != null) {
            it.value = billBuilder.totalAmountAsString()
        }
    }
    var cancel = false

    var billPartView = BillPartView(billBuilder)

    init {

        headerTitle = "New Bill"
        billNameTextField.addValueChangeListener {
            billBuilder.name = billNameTextField.value
        }
        billTotalAmountTextField.pattern = "(^\\d+[,.]\\d{2})|(^\\d+)"
        billTotalAmountTextField.errorMessage = "Invalid Number Format, expected 000.00"
        billTotalAmountTextField.addValueChangeListener {

            var totalAmount = parseAmount(billTotalAmountTextField.value)
            if (totalAmount != null) {
                billBuilder.totalAmount = totalAmount
                billPartView.billChange()
            }
        }
        val addButton = Button("Add") {
            if (billBuilder.isValid()) {
                this.close()
            } else {
                Notification.show("Error, some information is missing")
            }
        }
        addButton.addClickShortcut(Key.ENTER)
        val cancelButton = Button("Cancel") { e ->
            cancel = true
            this.close()
        }

        add(
            HorizontalLayout(billNameTextField, billTotalAmountTextField),
            billPartView,
            HorizontalLayout(addButton, cancelButton)
        )
    }


    fun openBillDialog(consumer: Consumer<Bill>) {
        this.addOpenedChangeListener { e ->
            if (!e.isOpened() && !cancel) {
                if (billBuilder.isValid()) {
                    consumer.accept(billBuilder.build()!!)
                } else {
                    Notification.show("Error, some information is missing")
                }
            }
        }
        this.open()
    }
}

fun parseAmount(value: String?): Int? {
    if (value == null) {
        return null;
    }
    var d = value.replace(",", ".").toDoubleOrNull() ?: return null
    if (d * 100 - (d * 100).toInt() > 0.0001) {
        return null
    } else {
        return (d * 100).toInt()
    }
}