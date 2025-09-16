package de.winkler.splitthebills.controller.ui.vaadin.views.dialog

import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.html.NativeTable
import com.vaadin.flow.component.html.NativeTableBody
import com.vaadin.flow.component.html.NativeTableCell
import com.vaadin.flow.component.html.NativeTableRow
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.NumberField
import com.vaadin.flow.component.textfield.TextField
import de.winkler.splitthebills.entity.Bill
import de.winkler.splitthebills.entity.BillBuilder
import de.winkler.splitthebills.entity.Person
import java.util.function.Consumer

class BillDialog(val billBuilder: BillBuilder) : Dialog() {

    constructor(persons: MutableList<Person>) : this(BillBuilder(persons))

    var table: NativeTable = NativeTable()


    var billNameTextField: TextField =
        TextField("Name", "Name").also {
            if (billBuilder.name != null) {
                it.value = billBuilder.name
            }
        }
    var billTotalAmountTextField = NumberField("Total Amount", "0.00").also {
        if (billBuilder.totalAmount != null) {
            it.value = billBuilder.totalAmount!!.toDouble() / 100
        }
    }
    var cancel = false


    init {

        headerTitle = "New Bill"
        billNameTextField.addValueChangeListener {
            billBuilder.name = billNameTextField.value
        }
        //billTotalAmountTextField.pattern = "(^\\d+[,.]\\d{2})|(^\\d+)"
        billTotalAmountTextField.errorMessage = "Invalid Number Format, expected 000.00"
        billTotalAmountTextField.addValueChangeListener {
            ui.get().access {
                var totalAmount = billTotalAmountTextField.value
                if (totalAmount != null) {
                    billBuilder.totalAmount = parseAmount(totalAmount)
                    billChange()
                }
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

        table.isEnabled = billBuilder.totalAmount != 0
        table.addBody().also {
            it.add(partsTableRows())
        }

        add(
            HorizontalLayout(billNameTextField, billTotalAmountTextField),
            table,
            HorizontalLayout(addButton, cancelButton)
        )
    }

    fun billChange() {
        table.isEnabled = billBuilder.totalAmount != 0
        table.removeBody()
        table.addBody().also {
            it.add(partsTableRows())
        }
    }

    fun partsTableRows(): List<NativeTableRow> = billBuilder.billParts.map {
        NativeTableRow(
            NativeTableCell(it.person.name),
            NativeTableCell(it.amount.toString()),
            NativeTableCell(
                if ((billBuilder.totalAmount ?: 0) > 0) String.format(
                    "%.3f%%",
                    it.part(billBuilder.totalAmount!!) * 100
                ) else "undefined"
            )
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

fun parseAmount(value: Double): Int? {
    if (value * 100 - (value * 100).toInt() > 0) {
        return null
    } else {
        return (value * 100).toInt()
    }
}