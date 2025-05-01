package de.winkler.splitthebills.service

import de.winkler.splitthebills.entity.Group
import de.winkler.splitthebills.service.repository.*
import org.springframework.stereotype.Component

@Component
class BillService(
    val groupRepository: GroupRepository,
    val personRepository: PersonRepository,
    val billPartRepository: BillPartRepository,
    val billRepository: BillRepository,
    val accountRepository: AccountRepository
) {

    fun listGroups(account_name: String): MutableList<Group> {
        var groups = groupRepository.findAll();
        var filtered: MutableList<Group> = ArrayList();
        for (group in groups) {
            if (hasAccount(group, account_name)) {
                filtered.add(group);
            }
        }
        return filtered;
    }

    fun hasAccount(group: Group, account_name: String): Boolean {
        for (account in group.accounts) {
            if (account.name.equals(account_name)) {
                return true;
            }
        }
        return false;

    }

    fun saveGroup(group: Group, account_name: String) {
        val account = accountRepository.findByName(account_name);
        if (!account.isPresent) {
            return;
        }

        group.accounts.add(account.get());
        saveGroup(group)

    }

    fun saveGroup(group: Group) {
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