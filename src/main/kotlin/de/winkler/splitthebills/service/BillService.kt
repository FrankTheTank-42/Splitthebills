package de.winkler.splitthebills.service

import de.winkler.splitthebills.entity.Group
import de.winkler.splitthebills.service.repository.BillRepository
import de.winkler.splitthebills.service.repository.BillPartRepository
import de.winkler.splitthebills.service.repository.GroupRepository
import de.winkler.splitthebills.service.repository.PersonRepository
import org.springframework.stereotype.Component

@Component
class BillService(
    val groupRepository: GroupRepository,
    val personRepository: PersonRepository,
    val billPartRepository: BillPartRepository,
    val billRepository: BillRepository
) {

    fun listBills(): MutableIterable<Group> {
        return groupRepository.findAll();
    }

    fun saveBill(group: Group) {
        for (p in group.persons) {
            if (!personRepository.existsById(p.id)) {
                personRepository.save(p);

            }
        }
        for (be in group.entries) {
            for (bp in be.billParts) {
                if (!billPartRepository.existsById(bp.id)) {
                    billPartRepository.save(bp);
                }
            }
            if (!billRepository.existsById(be.id)) {
                billRepository.save(be);
            }
        }
        groupRepository.save(group);
    }
}