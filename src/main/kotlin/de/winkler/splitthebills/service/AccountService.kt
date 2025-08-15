package de.winkler.splitthebills.service

import com.vaadin.flow.server.VaadinRequest
import de.winkler.splitthebills.entity.Confirmation
import de.winkler.splitthebills.entity.NewAccount
import de.winkler.splitthebills.service.repository.AccountRepository
import de.winkler.splitthebills.service.repository.ConfirmationRepository
import org.springframework.context.MessageSource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.Locale

@Component
class AccountService(
    val accountRepository: AccountRepository,
    val confirmationRepository: ConfirmationRepository,
    val encoder: PasswordEncoder,
    val javaMailSender: JavaMailSender,
    val messages: MessageSource
) {

    fun register(newAccount: NewAccount, request: VaadinRequest) {
        var account = newAccount.toAccount(encoder)
        var confirmation = Confirmation(account)
        accountRepository.save(account)
        confirmationRepository.save(confirmation)
        var email = SimpleMailMessage()
        email.subject = "Register for Split the Bills"
        email.setTo(account.email)


        var confirmationLink = request.contextPath+ "/api/confirmAccount/"+ confirmation.token
        email.text =  messages.getMessage("message.regSuccLink", null, Locale.ENGLISH) + "\r\n" + confirmationLink



    }
}