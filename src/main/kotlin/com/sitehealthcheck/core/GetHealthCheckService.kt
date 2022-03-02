package com.sitehealthcheck.core

import com.sitehealthcheck.repository.HealthCheckRepository
import com.sitehealthcheck.HealthEntity
import org.springframework.stereotype.Service

@Service
class GetHealthCheckService(
    private val repository: HealthCheckRepository
) {

    fun getLastHealthInfo(): List<HealthEntity> = repository.findAll()
        .groupBy { healthCheck -> healthCheck.uri!! }
        .map { (_, group) ->
            group.maxByOrNull { healthCheck -> healthCheck.healthCheckTime!! }
        }
        .filterNotNull()
}