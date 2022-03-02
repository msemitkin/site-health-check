package com.sitehealthcheck.core

import com.sitehealthcheck.HealthEntity
import com.sitehealthcheck.Response
import com.sitehealthcheck.repository.HealthCheckRepository
import com.sitehealthcheck.repository.SiteRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.*

@Service
class UpdateHealthCheckService(
    private val siteRepository: SiteRepository,
    private val healthCheckRepository: HealthCheckRepository,
    private val healthCheckService: HealthCheckService
) {

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    fun updateHealthCheck() {
        val healthEntities = siteRepository.findAll()
            .map { it.uri!! }
            .associateWith { healthCheckService.check(it) }
            .map { (uri, response) -> uri to response }
            .map(::toHealthEntity)
        healthCheckRepository.saveAll(healthEntities)
    }

    private fun toHealthEntity(responsePair: Pair<String, Response>) =
        HealthEntity().apply {
            this.healthCheckTime = LocalDateTime.now()
            this.uri = responsePair.first
            this.responseTime = responsePair.second.responseTime
            this.responseStatusCode = responsePair.second.httpStatus.value()
        }

}