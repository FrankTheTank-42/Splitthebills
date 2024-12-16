package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.io.Serializable
import java.util.*

@Entity
class Person (val name: String): Serializable {
    @Id
    val id = UUID.randomUUID();

}