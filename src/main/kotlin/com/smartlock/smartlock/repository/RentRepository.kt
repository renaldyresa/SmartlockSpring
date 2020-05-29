package com.smartlock.smartlock.repository

import com.smartlock.smartlock.model.entity.Rent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RentRepository: JpaRepository<Rent, Int>