package de.winkler.splitthebills.service

import de.winkler.splitthebills.entity.Group
import de.winkler.splitthebills.service.repository.*
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

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
        var filtered= mutableListOf<Group>();
        for (group in groups) {
            if (hasAccount(group, account_name)) {
                filtered.add(group);
            }
        }
        return filtered;
    }

    fun findGroupById(account_name: String, groupid: String): Group? {
        return listGroups(account_name)
            .filter { g -> g.id.toString().equals(groupid) }
            .firstOrNull()
    }

    fun hasAccount(group: Group, account_name: String): Boolean {
        for (account in group.accounts) {
            if (account.name.equals(account_name)) {
                return true;
            }
        }
        return false;

    }

    fun addAccount(group: Group, account_name: String):Boolean{
        val account = accountRepository.findByName(account_name);
        if (!account.isPresent) {
            return false
        }

        if(group.accounts.contains(account.get())){
            return false
        }
        group.accounts.add(account.get());
        return true;
    }

    fun addAccountandSaveGroup(group: Group, account_name: String) {
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

    fun deleteGroup(id: UUID) {
        val groupO = groupRepository.findById(id);
        if(!groupO.isPresent){
            return
        }
        val group = groupO.get();

        for (be in group.entries) {
            for (bp in be.billParts) {
                billPartRepository.delete(bp);
            }

            billRepository.delete(be);
        }
        for (p in group.persons) {
            if (!personRepository.existsById(p.id)) {
                personRepository.delete(p);
            }
        }
        groupRepository.delete(group);
    }
}