package de.winkler.splitthebills.controller.ui

import de.winkler.splitthebills.entity.Group
import de.winkler.splitthebills.entity.NewAccount
import de.winkler.splitthebills.service.BillService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID


@Controller
@RequestMapping("/ui")
class UIController(val billService: BillService) {


    @GetMapping("/")
    fun home(model: Model): String {
        model["title"] = "Split The Bill"
        return "home"
    }

    @GetMapping("/overview")
    fun overview(model: Model): String {
        model["title"] = "Split The Bill"
        val bills = billService.listBills();

        model["bills"] = bills;
        return "overview"
    }

    @GetMapping("/bill/{billId}")
    fun bill(model: Model, @PathVariable billId: String): String {

        val uuid = UUID.fromString(billId);
        val bills = billService.listBills();
        val group: Group = bills.filter { b -> b.id.equals(uuid) }.first();

        model["title"] = "Split The Bill"
        model["bill"] = group;
        return "bill"
    }

    @GetMapping("/user/login")
    fun login(model: Model):String{
        return "login"
    }

    @GetMapping("/user/new")
    fun newAccount(model: Model):String{
        model["account"] = NewAccount();
        return "newAccount"
    }
}