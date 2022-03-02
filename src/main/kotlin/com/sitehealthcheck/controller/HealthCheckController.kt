package com.sitehealthcheck.controller

import com.sitehealthcheck.*
import com.sitehealthcheck.core.AddHealthCheckService
import com.sitehealthcheck.core.GetHealthCheckService
import com.sitehealthcheck.repository.SiteRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController(
    private val getHealthCheckService: GetHealthCheckService,
    private val addHealthCheckService: AddHealthCheckService,
    private val siteRepository: SiteRepository
) {

    @GetMapping("sites/statuses")
    fun getStatuses(): List<HealthEntity> = getHealthCheckService.getLastHealthInfo()

    @PostMapping("sites")
    fun addSite(@RequestBody uri: String) {
        addHealthCheckService.addHealthCheck(uri)
    }

    @GetMapping("sites")
    fun getSites(): List<SiteEntity> = siteRepository.findAll()

}