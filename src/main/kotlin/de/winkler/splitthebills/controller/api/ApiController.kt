package de.winkler.splitthebills.controller.api

import de.winkler.splitthebills.entity.*
import de.winkler.splitthebills.service.BillService
import de.winkler.splitthebills.service.repository.AccountRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/api")
class ApiController(
    val billService: BillService,
    val accountRepository: AccountRepository//,
    /*val encoder: PasswordEncoder*/
) {

    @GetMapping("/bill")
    fun listBills(principal: Principal): MutableIterable<Group> {
        println("get bills")

        println(principal.name);
        return billService.listGroups(principal.name);
    }

    @GetMapping("/groups")
    fun getGroups(@AuthenticationPrincipal principal: String) :List<Group>{
        return billService.listGroups(principal);
    }

    @PostMapping("/group/create")
    fun createGroup(principal: Principal, name: String): Boolean {

        if (billService.groupRepository.existsByName(name)) {
            return false;
        }

        val group = Group(name);

        val account = accountRepository.findByName(principal.name);

        group.accounts.add(account.get());

        try {
            billService.groupRepository.save(group);
            return true;
        } catch (e: Exception) {
            e.printStackTrace()
            return false;
        }

    }

    @PostMapping("/account/create")
    fun createAccount(newAccount: NewAccount): Boolean {
        if (!newAccount.isComplete()) {
            return false;
        }

        val account = Account("1", "2", "3");// newAccount.toAccount(encoder)

        if (accountRepository.existsByName(account.name)) {
            return false;
        }
        try {
            accountRepository.save(account);
            return true;
        } catch (e: Exception) {
            e.printStackTrace()
            return false;
        }

    }

}
