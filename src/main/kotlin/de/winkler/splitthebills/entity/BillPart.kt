package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.io.Serializable
import java.util.*

@Entity
class BillPart(
    val person: Person,
    var amount: Int,
    var bill: Bill
) : Serializable {
    @Id
    val id = UUID.randomUUID();

    fun part(): Double {
        if (bill.totalamount == 0) {
            return 0.0
        } else {
            return amount.toDouble() / bill.totalamount.toDouble()
        }
    }
}