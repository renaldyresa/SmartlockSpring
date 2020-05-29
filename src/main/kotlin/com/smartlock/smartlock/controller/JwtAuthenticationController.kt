package com.smartlock.smartlock.controller

import com.smartlock.smartlock.model.AuthenticationRequest
import com.smartlock.smartlock.model.AuthenticationResponse
import com.smartlock.smartlock.security.UserDetailService
import com.smartlock.smartlock.util.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
class JwtAuthenticationController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var  jwtTokenUtil: JwtUtil

    @Autowired
    private lateinit var userDetailService: UserDetailService

    @RequestMapping(value = ["/authenticate"], method = [RequestMethod.POST])
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<*>? {
        try {
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
            )
        } catch (e: BadCredentialsException) {
            throw Exception("Incorrect username or password", e)
        }
        val userDetails: UserDetails = userDetailService
                .loadUserByUsername(authenticationRequest.username)
        val jwt: String = jwtTokenUtil.generateToken(userDetails)
        return ResponseEntity.ok(AuthenticationResponse(jwt))
    }
}