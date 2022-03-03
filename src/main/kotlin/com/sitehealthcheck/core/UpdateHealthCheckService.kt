package com.sitehealthcheck.core

import com.sitehealthcheck.HealthEntity
import com.sitehealthcheck.Response
import com.sitehealthcheck.repository.HealthCheckRepository
import com.sitehealthcheck.repository.SiteRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.util.StopWatch
import java.time.LocalDateTime
import java.util.concurrent.*

@Service
class UpdateHealthCheckService(
    private val siteRepository: SiteRepository,
    private val healthCheckRepository: HealthCheckRepository,
    private val healthCheckService: HealthCheckService
) {
    private val logger = LoggerFactory.getLogger(UpdateHealthCheckService::class.java)

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    fun updateHealthCheck() {
        val stopWatch = StopWatch().apply { start() }
        val healthEntities = siteRepository.findAll()
            .map { it.uri!! }
            .associateWith { healthCheckService.check(it) }
            .map { (uri, response) -> uri to response }
            .map(::toHealthEntity)
        healthCheckRepository.saveAll(healthEntities)
        logger.info("Updating of statuses took ${stopWatch.run { stop(); lastTaskTimeMillis }} ms")
    }

    private fun toHealthEntity(responsePair: Pair<String, Response>) =
        HealthEntity().apply {
            this.healthCheckTime = LocalDateTime.now()
            this.uri = responsePair.first
            this.responseTime = responsePair.second.responseTime
            this.responseStatusCode = responsePair.second.httpStatus.value()
        }

}