package de.winkler.splitthebills.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.io.Serializable
import java.util.*

@Entity
class Bill(
    var name: String,
    var totalamount: Int,
    var mainPayer: Person,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL]) val billParts: MutableList<BillPart>,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0
) : Serializable {

    var mode = Mode.PARTS

    enum class Mode {
        EQUAL,
        PARTS
    }

    fun totalAmountAsString() = (totalamount.toDouble() / 100).toString()
}

class BillBuilder() {

    constructor(bill: Bill) : this() {
        this.name = bill.name
        this.totalAmount = bill.totalamount
        this.billParts = bill.billParts
        this.bill = bill

    }

    constructor(persons: MutableList<Person>) : this() {

        setPersons(persons)
    }

    var name: String? = null
    var totalAmount: Int? = null
        get() = field
        set(value) {
            field = value

            var diff = totalAmount!! - sumBillParts()!!
            var bp = if (bill == null) billParts.firstOrNull() else billParts.find { it.person == bill!!.mainPayer }
            if (diff != 0 && bp != null) {
                bp.amount += diff

            }
        }

    var bill: Bill? = null
    lateinit var billParts: MutableList<BillPart>

    fun setPersons(persons: MutableList<Person>) {
        billParts = mutableListOf()
        billParts.addAll(persons.map { BillPart(it, 0) })
    }

    fun isValid(): Boolean {
        return (totalAmount != null) && (totalAmount != 0) && !name.isNullOrEmpty() && (bill?.billParts != null) && sumBillParts() == totalAmount
    }

    fun totalAmountAsString() = if (totalAmount == null) "0.00" else (totalAmount!!.toDouble() / 100).toString()

    fun sumBillParts() = billParts?.fold(0) { acc, next -> acc + next.amount }

    fun build(): Bill? {
        if (totalAmount == null || name == null) {
            return null
        } else {
            if (bill == null) {
                bill = Bill(name!!, totalAmount!!, billParts.first().person, billParts)
            }
            bill!!.name = name!!
            bill!!.totalamount = totalAmount!!
            return bill
        }
    }
}