package de.winkler.splitthebills.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.TemporalType
import org.springframework.data.jpa.repository.Temporal
import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Entity
class Confirmation(
    val token: String,

    @Temporal(TemporalType.TIMESTAMP) val createdDate: LocalDateTime,
    @OneToOne val account: Account
) : Serializable {
    @Id
    var id: UUID = UUID.randomUUID()

    constructor(account: Account) : this(UUID.randomUUID().toString(), LocalDateTime.now(), account)
}