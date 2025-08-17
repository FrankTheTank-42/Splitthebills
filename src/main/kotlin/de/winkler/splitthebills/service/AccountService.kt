package de.winkler.splitthebills.service

import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.server.VaadinRequest
import de.winkler.splitthebills.entity.Account
import de.winkler.splitthebills.entity.Confirmation
import de.winkler.splitthebills.entity.NewAccount
import de.winkler.splitthebills.service.repository.AccountRepository
import de.winkler.splitthebills.service.repository.ConfirmationRepository
import jakarta.servlet.ServletContext
import org.springframework.context.MessageSource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.Locale

@Component
class AccountService(
    val accountRepository: AccountRepository,
    val confirmationRepository: ConfirmationRepository,
    val encoder: PasswordEncoder,
    val javaMailSender: JavaMailSender,
    val messages: MessageSource,
    val context: ServletContext
) {

    fun register(newAccount: NewAccount) {
        println("Context" + context.contextPath)
        var account = newAccount.toAccount(encoder)
        accountRepository.save(account)
        send(account)
    }

    fun send(account: Account) {
        var confirmation = Confirmation(account)
        confirmationRepository.save(confirmation)

        //Costruct Mail
        var email = SimpleMailMessage()
        email.subject = "Register for Split the Bills"
        email.setTo(account.email)
        email.setFrom("support@test.de")
        var contextpath = "http://localhost:8080"
        var confirmationLink = contextpath + "/api/confirmAccount/" + confirmation.token
        var text = "Please  Confirm" //messages.getMessage("message.regSuccLink", null, Locale.ENGLISH);
        email.text = text + "\r\n" + confirmationLink

        //Send Mail
        javaMailSender.send(email)

    }

    @Transactional
    fun resend(accountName: String) {
        var account = accountRepository.findByName(accountName)
        if (account.isPresent) {
            confirmationRepository.deleteByAccount(account.get())
            send(account.get())
        }

    }
}