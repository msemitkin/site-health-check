package com.sitehealthcheck.core

import com.sitehealthcheck.repository.HealthCheckRepository
import com.sitehealthcheck.HealthEntity
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class GetHealthCheckService(
    private val repository: HealthCheckRepository
) {

    companion object {
        private val ALIVE_STATUSES =
            setOf(HttpStatus.OK.value(), HttpStatus.FORBIDDEN.value(), HttpStatus.FOUND.value())
    }

    fun getHealthInfo(): List<HealthInfo> {
        val healthInfo = repository.findAll()
            .groupBy { healthCheck -> healthCheck.uri!! }
            .map { (_, group) ->
                group.maxByOrNull { healthCheck -> healthCheck.healthCheckTime!! }
            }
            .filterNotNull()
        return healthInfo.map(this::mapToHealthStatus)
    }

    private fun mapToHealthStatus(healthEntity: HealthEntity): HealthInfo {
        return if (healthEntity.responseStatusCode !in ALIVE_STATUSES) {
            HealthInfo(healthEntity.uri!!, HealthStatus.DOWN)
        } else if (healthEntity.responseTime!! > Duration.ofSeconds(10).toMillis()) {
            HealthInfo(healthEntity.uri!!, HealthStatus.DOWN)
        } else {
            HealthInfo(healthEntity.uri!!, HealthStatus.UP)
        }
    }
}