package com.smartlock.smartlock.controller.api

import com.smartlock.smartlock.model.entity.Door
import com.smartlock.smartlock.repository.DoorRepository
import com.smartlock.smartlock.response.DefaultResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/api/door")
class DoorController(@Autowired private val doorRepository: DoorRepository) {

    @GetMapping
    fun getAll(): ResponseEntity<DefaultResponse<Door>> {
        val resDoor: ArrayList<Door> = doorRepository.findAll() as ArrayList<Door>
        val response: DefaultResponse<Door> = DefaultResponse(true, null, resDoor)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/{idDoor}")
    fun getById(@PathVariable("idDoor") idDoor: String): ResponseEntity<DefaultResponse<Door>>{
        val response: DefaultResponse<Door> = doorRepository.findById(idDoor).map {
                DefaultResponse(true, null, arrayListOf(it))
            }.orElse(DefaultResponse(false, "Not Found id: $idDoor"))
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping
    fun add(@RequestBody nDoor: Door) : ResponseEntity<DefaultResponse<Door>> {
        val cDoor = Door(
                idDoor = "dr" + UUID.randomUUID().toString().replace("-", "").substring(0, 10),
                token = "tkn" + UUID.randomUUID().toString().replace("-", "").substring(0, 10),
                pin = nDoor.pin,
                statusDoor = false,
                statusRent = false,
                rent = null,
                owner = nDoor.owner
        )
        val resDoor: ArrayList<Door>
        var response: DefaultResponse<Door>
        try {
            resDoor = arrayListOf(doorRepository.save(cDoor))
            response = DefaultResponse(true, "success created door", resDoor)
        }catch (e: Exception){
            response = DefaultResponse(false, "Failed created Data", null)
            return ResponseEntity(response, HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PutMapping("/{idDoor}")
    fun update(@PathVariable("idDoor") idDoor: String, @RequestBody nDoor: Door): ResponseEntity<DefaultResponse<Door>> {
        val response: DefaultResponse<Door> = doorRepository.findById(idDoor).map {
            val resDoor: Door = it.copy(
                    token = nDoor.token,
                    pin = nDoor.pin,
                    statusRent = nDoor.statusRent,
                    statusDoor = nDoor.statusDoor,
                    rent = nDoor.rent,
                    owner = nDoor.owner
            )
            doorRepository.save(resDoor)
            DefaultResponse(true, "Success Update Door", arrayListOf(resDoor))
        }.orElse(DefaultResponse(false, "Id Not Found", null))
        return  if (response.status) ResponseEntity(response, HttpStatus.OK) else ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/{idDoor}")
    fun delete(@PathVariable("idDoor") idDoor: String): ResponseEntity<DefaultResponse<Door>>{
        val response: DefaultResponse<Door> = doorRepository.findById(idDoor).map {
            doorRepository.delete(it)
            DefaultResponse<Door>(true, "Success Delete Door", null)
        }.orElse(DefaultResponse(false, "Id Not Found", null))
        return if (response.status) ResponseEntity(response, HttpStatus.OK) else ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

}