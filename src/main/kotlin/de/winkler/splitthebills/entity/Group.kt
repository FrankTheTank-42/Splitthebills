package de.winkler.splitthebills.entity

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity(name = "GroupOfBills")
class Group(
    val name: String,
    @ManyToMany(fetch = FetchType.EAGER) val persons: MutableList<Person>,
    @OneToMany(fetch = FetchType.EAGER) val entries: MutableList<Bill>,
    @ManyToMany(fetch = FetchType.EAGER) val accounts: MutableList<Account>
) : Serializable {
    @Id
    val id:UUID = UUID.randomUUID();

    constructor(name: String) : this(name, mutableListOf(), mutableListOf(), mutableListOf())

}
