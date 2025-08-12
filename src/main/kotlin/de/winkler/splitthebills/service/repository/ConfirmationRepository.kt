package de.winkler.splitthebills.service.repository

import de.winkler.splitthebills.entity.Confirmation
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ConfirmationRepository : CrudRepository<Confirmation, String> {
    fun findByToken(token: String) : Optional<Confirmation>
}