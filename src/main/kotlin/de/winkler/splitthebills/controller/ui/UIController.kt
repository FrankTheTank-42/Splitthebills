package de.winkler.splitthebills.controller.ui

import de.winkler.splitthebills.entity.NewAccount
import de.winkler.splitthebills.service.BillService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal
import java.util.*


@Controller
@RequestMapping("/ui")
class UIController(val billService: BillService) {


    @GetMapping("/")
    fun home(model: Model): String {
        model["title"] = "Split The Bill"
        return "home"
    }

    @GetMapping("/overview")
    fun overview(principal: Principal, model: Model): String {
        model["title"] = "Split The Bill"
        val bills = billService.listGroups(principal.name);
        model["name"] = bills;
        return "overview"
    }


    @GetMapping("/groups")
    fun groups(principal: Principal, model: Model): String {
        val groups = billService.listGroups(principal.name);
        model["groups"] = groups;
        return "groups"
    }

    @GetMapping("/group/{groupId}")
    fun group(principal: Principal, model: Model, @PathVariable groupId: String): String {
        val group = billService.groupRepository.findById(UUID.fromString(groupId));
        if (!group.isPresent) {
            model["empty"] = true;
        }

        val authorized = group.get().accounts.filter { account -> account.name.equals(principal.name) }.isNotEmpty();
        if (authorized) {
            model["group"] = group;
        } else {
            model["authorized"] = false;
        }
        return "group"
    }

    @GetMapping("/user/login")
    fun login(model: Model): String {
        return "login"
    }

    @GetMapping("/user/new")
    fun newAccount(model: Model): String {
        model["account"] = NewAccount();
        return "newAccount"
    }
}