package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.security.crypto.password.PasswordEncoder
import java.io.Serializable
import java.util.UUID

@Entity
class Account(
    val name: String,
    val mail: String,
    val passwordhash: String,
) : Serializable {
    @Id
    val id = UUID.randomUUID();
}
