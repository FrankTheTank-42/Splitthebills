package de.winkler.splitthebills.service.repository

import de.winkler.splitthebills.entity.BillPart
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface BillPartRepository : CrudRepository<BillPart, UUID> {
}