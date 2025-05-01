package de.winkler.splitthebills.service.repository

import de.winkler.splitthebills.entity.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.security.Principal
import java.util.*

interface AccountRepository : CrudRepository<Account, String> {
    fun findByName(name: String) : Optional<Account>
    fun existsByName(name: String) : Boolean

}