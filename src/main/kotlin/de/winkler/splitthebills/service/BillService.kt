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
        var filtered = mutableListOf<Group>();
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

    @Transactional
    open fun newGroup(groupName: String, accountName: String): Group? {
        if (groupRepository.findAllByName(groupName)
                .count { hasAccount(it, accountName) }
            > 0
        ) {
            //group with that name already exists
            return null
        }
        var group = Group(groupName)
        addAccountToGroup(group, accountName)

        saveGroup(group)
        return group
    }


    fun addAccountToGroup(group: Group, account_name: String): Boolean {
        val account = accountRepository.findByName(account_name);
        if (!account.isPresent) {
            return false
        }

        if (group.accounts.contains(account.get())) {
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

    @Transactional
    fun saveGroup(group: Group) {
        groupRepository.save(group);
    }

    fun deleteGroup(id: Long) {
        groupRepository.deleteById(id)
    }
}