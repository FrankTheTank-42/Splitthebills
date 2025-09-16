package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.io.Serializable
import java.util.*

@Entity
class BillPart(
    val person: Person,
    var amount: Int,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0
) : Serializable {

    fun part(totalAmount: Int): Double {
        if (totalAmount == 0) {
            return 0.0
        } else {
            return amount.toDouble() / totalAmount.toDouble()
        }
    }
}