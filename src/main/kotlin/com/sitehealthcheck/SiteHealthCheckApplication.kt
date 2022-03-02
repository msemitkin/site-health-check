package com.sitehealthcheck

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SiteHealthCheckApplication

fun main(args: Array<String>) {
    runApplication<SiteHealthCheckApplication>(*args)
}
