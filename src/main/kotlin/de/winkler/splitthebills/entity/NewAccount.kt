package de.winkler.splitthebills.entity

import org.springframework.security.crypto.password.PasswordEncoder

class NewAccount(
    var name: String?,
    var mail: String?,
    var password: String?
) {

    constructor() : this(null, null, null)

    fun isComplete(): Boolean {
        return !(name == null || mail == null || password == null)
    }

    fun toAccount(encoder: PasswordEncoder): Account {

        if (!isComplete()) {
            throw IllegalArgumentException("not all properties are initialize")
        }

        val hash = encoder.encode(password!!);
        return Account(name!!, mail!!, hash)

    }
}

