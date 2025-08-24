package de.winkler.splitthebills.controller.ui.vaadin.views.dialog

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import de.winkler.splitthebills.entity.Bill
import java.util.function.Consumer

class NewBillDialog : Dialog() {
    var billNameTextField = TextField("Name")
    var billTotalAmountTextField = TextField("Total Amount")
    var cancel = false

    init {
        headerTitle = "New Bill"
        billNameTextField.placeholder = "Name"
        billTotalAmountTextField.placeholder = "0.0"

        val addButton = Button("Add") { e -> this.close() }
        val cancelButton = Button("Cancel") { e ->
            cancel = true
            this.close()
        }
        add(
            HorizontalLayout(billNameTextField, billTotalAmountTextField),
            HorizontalLayout(addButton, cancelButton)
        )
    }


    fun openNameDialog(consumer: Consumer<Bill>) {
        this.addOpenedChangeListener { e ->
            // if dialog was closed

            if (!e.isOpened() && !cancel) {
                // do something with the `name`
                var totalAmount = parseAmount(billTotalAmountTextField.value)
                if (totalAmount == null) {
                    Notification.show("Please add a valid amount")
                } else {
                    var bill = Bill(billNameTextField.value, totalAmount)
                    consumer.accept(bill)
                }
            }
        }
        this.open()
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


}