package de.winkler.splitthebills.service

import de.winkler.splitthebills.entity.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UserDetailsImpl(val account: Account) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        authorities.add(SimpleGrantedAuthority(account.name))
        authorities.add(SimpleGrantedAuthority("USER"))
        return authorities
    }

    override fun getPassword(): String {
        return account.passwordhash
    }

    override fun getUsername(): String {
        return account.name
    }

}