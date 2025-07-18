package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.io.Serializable
import java.util.*

@Entity
class Account(
    val name: String,
    val mail: String,
    val passwordhash: String,
) : Serializable {
    @Id
    val id = UUID.randomUUID();
}
