package de.winkler.splitthebills.service

import de.winkler.splitthebill.service.repository.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(val accountRepository: AccountRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrEmpty()) {
            throw UsernameNotFoundException(username);
        }

        val account = accountRepository.findById(username);
        if (!account.isPresent) {
            throw UsernameNotFoundException(username);
        }
        return de.winkler.splitthebills.service.UserDetailsImpl(account.get());
    }
}