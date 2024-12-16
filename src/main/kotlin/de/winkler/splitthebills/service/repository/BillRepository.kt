package de.winkler.splitthebills.service.repository

import de.winkler.splitthebill.entity.Bill
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface BillRepository: CrudRepository<Bill, UUID> {
}