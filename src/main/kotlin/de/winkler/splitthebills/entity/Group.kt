package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import java.io.Serializable
import java.util.UUID

@Entity(name = "GroupOfBills")
class Group(
    val name: String,
    @ManyToMany(fetch = FetchType.EAGER) val persons: MutableList<Person>,
    @OneToMany(fetch = FetchType.EAGER) val entries: MutableList<Bill>,
    @ManyToMany(fetch = FetchType.EAGER) val accounts: MutableList<Account>
) : Serializable {
    @Id
    val id = UUID.randomUUID();

    constructor(name: String) : this(name, ArrayList(), ArrayList(), ArrayList())

}
