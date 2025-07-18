package de.winkler.splitthebills.service.repository

import de.winkler.splitthebills.entity.Account
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AccountRepository : CrudRepository<Account, String> {
    fun findByName(name: String) : Optional<Account>
    fun existsByName(name: String) : Boolean

}