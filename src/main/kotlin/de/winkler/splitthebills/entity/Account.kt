package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.io.Serializable
import java.security.MessageDigest

@Entity
class Account(@Id val name: String, val mail: String, val password: String, val yourself: Person) : Serializable {


}


class NewAccount(var name: String?, var mail: String?, var password: String?, var yourself: Person?) {

    constructor() : this(null, null, null, null)

    fun isComplete(): Boolean {
        return !(name == null || mail == null || password == null || yourself == null)
    }

    fun toAccount(): Account {

        if (name == null || mail == null || password == null || yourself == null) {
            throw IllegalArgumentException("not all properties are initialize")
        }

        return Account(name!!, mail!!, password!!, yourself!!)

    }
}

