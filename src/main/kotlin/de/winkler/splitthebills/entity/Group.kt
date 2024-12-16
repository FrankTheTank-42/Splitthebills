package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.io.Serializable
import java.util.UUID

@Entity
class Group(
    val name: String,
    @OneToMany val persons: MutableList<Person>,
    @OneToMany val entries: MutableList<Bill>
) : Serializable {
    @Id
    val id = UUID.randomUUID();

}