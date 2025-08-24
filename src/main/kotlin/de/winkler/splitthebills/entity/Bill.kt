package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.io.Serializable
import java.util.*

@Entity
class Bill (val name: String,
            val totalamount: Int,
            @OneToMany(fetch = FetchType.EAGER) val billParts: List<BillPart>) : Serializable{
    @Id
    val id = UUID.randomUUID();

    constructor(name:String, totalamount: Int): this(name, totalamount, mutableListOf<BillPart>())
}