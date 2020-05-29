package com.smartlock.smartlock.controller.api

import com.smartlock.smartlock.model.entity.Door
import com.smartlock.smartlock.model.entity.Rent
import com.smartlock.smartlock.repository.RentRepository
import com.smartlock.smartlock.response.DefaultResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList


@RestController
@RequestMapping("/api/rent")
class RentController(@Autowired private val rentRepository: RentRepository) {

    @GetMapping
    fun getAll(): ResponseEntity<DefaultResponse<Rent>> {
        val resDoor: ArrayList<Rent> =  rentRepository.findAll() as ArrayList<Rent>
        val response: DefaultResponse<Rent> =  DefaultResponse(true, null, resDoor)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/{idRent}")
    fun getById(@PathVariable("idRent") idRent: Int): ResponseEntity<DefaultResponse<Rent>>{
        val response: DefaultResponse<Rent> = rentRepository.findById(idRent).map {
                DefaultResponse(true, null, arrayListOf(it))
            }.orElse(DefaultResponse(false, "Not Found id: $idRent"))
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping
    fun add(@RequestBody nRent: Rent) : ResponseEntity<DefaultResponse<Rent>> {
        val currentDateTime = LocalDateTime.now()
        val cRent = Rent(
                orderDate = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                dateIn = nRent.dateIn,
                dateOut = nRent.dateOut,
                idDoor = nRent.idDoor,
                username = nRent.username
        )
        val resRent: ArrayList<Rent>
        var response: DefaultResponse<Rent>
        try {
            resRent = arrayListOf(rentRepository.save(cRent))
            response = DefaultResponse(true, "success created", resRent)
        }catch (e: Exception){
            response = DefaultResponse(false, "Failed created", null)
            return ResponseEntity(response, HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PutMapping("/{idRent}")
    fun update(@PathVariable("idRent") idRent: String, @RequestBody nRent: Rent): ResponseEntity<DefaultResponse<Rent>> {
        val response: DefaultResponse<Rent> = rentRepository.findById(idRent.toInt()).map {
            val resRent: Rent = it.copy(
                    dateIn = nRent.dateIn,
                    dateOut = nRent.dateOut,
                    idDoor = nRent.idDoor,
                    username = nRent.username
            )
            rentRepository.save(resRent)
            DefaultResponse(true, "Success Update Rent", arrayListOf(resRent))
        }.orElse(DefaultResponse(false, "Id Not Found", null))
        return  if (response.status) ResponseEntity(response, HttpStatus.OK) else ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/{idRent}")
    fun delete(@PathVariable("idRent") idRent: Int): ResponseEntity<DefaultResponse<Rent>>{
        val response: DefaultResponse<Rent> = rentRepository.findById(idRent).map {
            rentRepository.delete(it)
            DefaultResponse<Rent>(true, "Success Delete Door", null)
        }.orElse(DefaultResponse(false, "Id Not Found", null))
        return if (response.status) ResponseEntity(response, HttpStatus.OK) else ResponseEntity(response, HttpStatus.NOT_FOUND)
    }


}