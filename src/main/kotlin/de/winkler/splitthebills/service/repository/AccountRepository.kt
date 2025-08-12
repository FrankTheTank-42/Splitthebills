package de.winkler.splitthebills.service.repository

import de.winkler.splitthebills.entity.Account
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AccountRepository : CrudRepository<Account, Long> {
    fun findByName(name: String) : Optional<Account>
    fun existsByName(name: String) : Boolean
    fun findByEmailIgnoreCase(email: String): Optional<Account>
    fun existsByEmail(email:String): Boolean
}