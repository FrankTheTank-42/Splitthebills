package de.winkler.splitthebills.service.repository

import de.winkler.splitthebills.entity.Person
import org.springframework.data.repository.CrudRepository
import java.util.*

interface PersonRepository : CrudRepository<Person, Long> {
}