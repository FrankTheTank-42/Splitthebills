package de.winkler.splitthebills.service.repository

import de.winkler.splitthebills.entity.BillPart
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BillPartRepository : CrudRepository<BillPart, Long> {
}