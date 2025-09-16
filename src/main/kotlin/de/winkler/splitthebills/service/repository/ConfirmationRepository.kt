package de.winkler.splitthebills.service.repository

import de.winkler.splitthebills.entity.Account
import de.winkler.splitthebills.entity.Confirmation
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface ConfirmationRepository : CrudRepository<Confirmation, Long> {
    fun findByToken(token: String) : Optional<Confirmation>
    @Transactional
    fun deleteByAccount(account: Account)
}