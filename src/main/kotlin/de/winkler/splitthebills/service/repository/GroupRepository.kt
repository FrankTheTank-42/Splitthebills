package de.winkler.splitthebills.service.repository

import de.winkler.splitthebill.entity.Group
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface GroupRepository : CrudRepository<Group, UUID> {


}