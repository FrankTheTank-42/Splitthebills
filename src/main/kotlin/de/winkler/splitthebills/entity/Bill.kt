package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.io.Serializable
import java.util.*

@Entity
class Bill(
    var name: String,
    var totalamount: Int,
    var mainPayer: Person,
    @OneToMany(fetch = FetchType.EAGER) val billParts: MutableList<BillPart>
) : Serializable {
    @Id
    val id = UUID.randomUUID();
    var mode = Mode.PARTS

    enum class Mode {
        EQUAL,
        PARTS
    }

    fun totalAmountAsString() = (totalamount.toDouble() / 100).toString()
}


fun defaultBill(name: String, totalamount: Int, persons: MutableList<Person>): Bill {
    var billParts = mutableListOf<BillPart>()
    var bill = Bill(name, totalamount, persons.first(), billParts)
    billParts.addAll(persons.map { BillPart(it, 0, bill) })
    billParts.first().amount = totalamount
    return bill
}

class BillBuilder() {

    constructor(bill: Bill) : this() {
        this.name = bill.name
        this.totalAmount = bill.totalamount
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
            if (bill != null) {
                var diff = sumBillParts()!! - totalAmount!!
                var bp = bill!!.billParts.find { it.person == bill!!.mainPayer }
                if (diff != 0 && bp != null) {
                    bp.amount += diff
                }
            }
        }
    var bill: Bill? = null
        get() = if (field == null || totalAmount == null && name == null) {
            null
        } else {
            field!!.name = name!!
            field!!.totalamount = totalAmount!!
            var diff = sumBillParts()!! - totalAmount!!
            var bp = field!!.billParts.find { it.person == field!!.mainPayer }
            if (diff != 0 && bp != null) {
                bp.amount += diff
            }
            field
        }

    fun setName(name: String): BillBuilder {
        this.name = name
        return this
    }


    fun setPersons(persons: MutableList<Person>) {
        var billParts = mutableListOf<BillPart>()
        bill = Bill("", 0, persons.first(), billParts)
        billParts.addAll(persons.map { BillPart(it, 0, bill!!) })
    }

    fun isValid(): Boolean {
        return (totalAmount != null) && (totalAmount != 0) && !name.isNullOrEmpty() && (bill?.billParts != null) && sumBillParts() == totalAmount
    }

    fun totalAmountAsString() = if (totalAmount == null) "0.00" else (totalAmount!!.toDouble() / 100).toString()

    fun sumBillParts() = bill?.billParts?.fold(0) { acc, next -> acc + next.amount }

    fun build(): Bill? {
        return bill

    }

}