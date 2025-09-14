package de.winkler.splitthebills.service.repository

import de.winkler.splitthebills.entity.Group
import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.util.*

interface GroupRepository : CrudRepository<Group, UUID> {

    fun existsByName(name: String): Boolean

    fun findAllByName(name: String): List<Group>

}
