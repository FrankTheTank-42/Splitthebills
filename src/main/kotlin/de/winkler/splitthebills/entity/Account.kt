package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.io.Serializable
import java.util.*

@Entity
class Account(
    val name: String,
    val email: String,
    val passwordhash: String,
    var isEnabled: Boolean = false
) : Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: Long = 0;
}
