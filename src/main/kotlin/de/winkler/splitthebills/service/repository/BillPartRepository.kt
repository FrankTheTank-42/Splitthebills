package de.winkler.splitthebills.service.repository

import de.winkler.splitthebill.entity.BillPart
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.Repository
import java.util.UUID

interface BillPartRepository : CrudRepository<BillPart, UUID> {
}