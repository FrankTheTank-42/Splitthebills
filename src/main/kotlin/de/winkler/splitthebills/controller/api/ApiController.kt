package de.winkler.splitthebills.controller.api

import de.winkler.splitthebill.entity.*
import de.winkler.splitthebill.service.BillService
import de.winkler.splitthebill.service.repository.AccountRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class ApiController(val billService: BillService, val accountRepository: AccountRepository) {

    @GetMapping("/bill")
    fun listBills(): MutableIterable<Group> {

        println("get bills")
        return billService.listBills();

    }

    @GetMapping("/bill/addOne")
    fun addOne(): Long {
        println("add bills")
        val me = Person("me")
        val persons: MutableList<Person> = mutableListOf(me)
        val billentries: MutableList<BillPart> = mutableListOf(BillPart(me, 10.0))
        val b1 = Group("mybill", persons, ArrayList<Bill>())
        billService.saveBill(b1);

        return billService.groupRepository.count();
    }

    @PostMapping("/account/create")
    fun createAccount(newAccount: NewAccount): Boolean {
        if(!newAccount.isComplete()){
            return false;
        }

        val account = newAccount.toAccount()

        if (accountRepository.existsById(account.name)) {
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
