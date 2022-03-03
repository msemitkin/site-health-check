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
        val uris: List<String> = siteRepository.findAll().map { it.uri!! }
        if (uris.isNotEmpty()) {
            val stopWatch = StopWatch().apply { start() }
            val executors: ExecutorService = Executors.newFixedThreadPool(uris.size)

            val uriToFutureResponse: Map<String, Future<Response>> = uris
                .associateWith { uri -> Callable { healthCheckService.check(uri) } }
                .mapValues { (_, task) -> executors.submit(task) }

            val healthEntities: List<HealthEntity> = uriToFutureResponse
                .mapValues { (_, value) -> value.get() }
                .map { (uri, response) -> toHealthEntity(uri, response) }

            healthCheckRepository.saveAll(healthEntities)
            logger.info("Updating of statuses took ${stopWatch.run { stop(); lastTaskTimeMillis }} ms")
        }
    }

    private fun toHealthEntity(uri: String, response: Response) =
        HealthEntity().apply {
            this.healthCheckTime = LocalDateTime.now()
            this.uri = uri
            this.responseTime = response.responseTime
            this.responseStatusCode = response.httpStatus.value()
        }

}