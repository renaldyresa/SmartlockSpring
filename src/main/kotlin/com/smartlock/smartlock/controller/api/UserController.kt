package com.smartlock.smartlock.controller.api

import com.smartlock.smartlock.model.entity.User
import com.smartlock.smartlock.repository.UserRepository
import com.smartlock.smartlock.response.DefaultResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(@Autowired private val userRepository: UserRepository) {

    @GetMapping
    fun getAll(): ResponseEntity<DefaultResponse<User>> {
        val resUser: ArrayList<User> = userRepository.findAll() as ArrayList<User>
        val response: DefaultResponse<User> = DefaultResponse(true, null, resUser)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/{username}")
    fun getById(@PathVariable("username") username: String): ResponseEntity<DefaultResponse<User>>{
        val response: DefaultResponse<User> = userRepository.findByUsername(username).map {
            DefaultResponse(true, null, arrayListOf(it))
        }.orElse(DefaultResponse(false, "Not Found id: $username"))
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping
    fun add(@RequestBody nUser: User) : ResponseEntity<DefaultResponse<User>> {
        val cUser = User(
                username = nUser.username,
                password = nUser.password,
                email = nUser.email,
                name = nUser.name
        )
        val resUser: ArrayList<User>
        var response: DefaultResponse<User>
        try {
            resUser = arrayListOf(userRepository.save(cUser))
            response = DefaultResponse(true, "success created", resUser)
        }catch (e: Exception){
            response = DefaultResponse(false, "Failed created", null)
            return ResponseEntity(response, HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PutMapping("/{username}")
    fun update(@PathVariable("username") username: String, @RequestBody nUser: User): ResponseEntity<DefaultResponse<User>> {
        val response: DefaultResponse<User> = userRepository.findByUsername(username).map {
            val resUser: User = it.copy(
                    active = nUser.active,
                    username = nUser.username,
                    password = nUser.password,
                    email = nUser.email,
                    name = nUser.name
            )
            userRepository.save(resUser)
            DefaultResponse(true, "Success Update Rent", arrayListOf(resUser))
        }.orElse(DefaultResponse(false, "Id Not Found", null))
        return  if (response.status) ResponseEntity(response, HttpStatus.OK) else ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

}