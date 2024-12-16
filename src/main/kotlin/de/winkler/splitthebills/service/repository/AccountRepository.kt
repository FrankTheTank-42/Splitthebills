package de.winkler.splitthebills.service.repository

import de.winkler.splitthebill.entity.Account
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AccountRepository : CrudRepository<Account, String> {
}