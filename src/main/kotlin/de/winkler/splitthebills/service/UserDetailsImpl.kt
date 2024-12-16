package de.winkler.splitthebills.service

import de.winkler.splitthebill.entity.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UserDetailsImpl(val account: Account) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        authorities.add(SimpleGrantedAuthority(account.yourself.id.toString()))
        return authorities
    }

    override fun getPassword(): String {
        return account.password
    }

    override fun getUsername(): String {
        return account.name
    }

}