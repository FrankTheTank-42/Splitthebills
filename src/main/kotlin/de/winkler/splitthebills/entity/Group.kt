package de.winkler.splitthebills.entity

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity(name = "GroupOfBills")
class Group(
    val name: String,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL]) val persons: MutableList<Person>,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL]) val entries: MutableList<Bill>,
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL]) val accounts: MutableList<Account>,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0
) : Serializable {

    constructor(name: String) : this(name, mutableListOf(), mutableListOf(), mutableListOf())

}
