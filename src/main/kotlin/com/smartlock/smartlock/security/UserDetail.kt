package com.smartlock.smartlock.security

import com.smartlock.smartlock.model.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetail(private val user: User) : UserDetails {

    private var username: String = user.username
    private var password: String = user.password
    private var active: Boolean = user.active
    private var authority = ArrayList<GrantedAuthority>()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        user.role.split(",").forEach { role ->
            authority.add(SimpleGrantedAuthority(role))
        }
        return authority
    }

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    override fun isEnabled(): Boolean {
        return active
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}