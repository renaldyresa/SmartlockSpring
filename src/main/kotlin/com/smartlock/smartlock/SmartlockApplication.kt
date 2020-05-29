package com.smartlock.smartlock

import com.smartlock.smartlock.repository.UserRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = [UserRepository::class])
class SmartlockApplication

fun main(args: Array<String>) {
	runApplication<SmartlockApplication>(*args)
}
