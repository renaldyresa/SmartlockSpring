package com.smartlock.smartlock.repository

import com.smartlock.smartlock.model.entity.Door
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DoorRepository: JpaRepository<Door, String>