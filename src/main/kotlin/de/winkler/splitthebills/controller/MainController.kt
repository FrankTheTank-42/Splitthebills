package de.winkler.splitthebills.controller

import de.winkler.splitthebill.entity.Group
import de.winkler.splitthebill.service.BillService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID


@Controller
class MainController(val billService: BillService) {


    @GetMapping("/")
    fun home(model: Model): String {
        model["title"] = "Split The Bill"
        return "home"
    }

    @GetMapping("/overview")
    fun overview(model: Model): String {
        model["title"] = "Split The Bill"
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
}