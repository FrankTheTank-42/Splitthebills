package de.winkler.splitthebills.service.repository

import de.winkler.splitthebills.entity.Group
import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.util.UUID

interface GroupRepository : CrudRepository<Group, UUID> {

    fun existsByName(name: String) : Boolean

    fun findAllByName(@AuthenticationPrincipal name: String):MutableIterable<Group> {
        var groups = findAll();
        var filtered: MutableList<Group> = ArrayList();
        for (group in groups) {
            if (hasAccount(group, name)) {
                filtered.add(group);
            }
        }
        return filtered;
    }


}

fun hasAccount(group: Group, account_name: String): Boolean {
    for (account in group.accounts) {
        if (account.name.equals(account_name)) {
            return true;
        }
    }
    return false;
}