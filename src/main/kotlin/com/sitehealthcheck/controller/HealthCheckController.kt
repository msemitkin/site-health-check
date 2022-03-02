package com.sitehealthcheck.controller

import com.sitehealthcheck.*
import com.sitehealthcheck.core.GetHealthCheckService
import com.sitehealthcheck.core.HealthInfo
import com.sitehealthcheck.core.SiteService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController(
    private val getHealthCheckService: GetHealthCheckService,
    private val siteService: SiteService
) {

    @GetMapping("sites/statuses")
    fun getStatuses(): List<HealthInfo> = getHealthCheckService.getHealthInfo()

    @PostMapping("sites")
    fun addSite(@RequestBody uri: String) {
        siteService.saveSite(uri)
    }

    @GetMapping("sites")
    fun getSites(): List<SiteEntity> = siteService.getAllSites()

}