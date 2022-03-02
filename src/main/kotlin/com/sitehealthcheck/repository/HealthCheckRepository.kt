package com.sitehealthcheck.repository

import com.sitehealthcheck.HealthEntity
import org.springframework.data.jpa.repository.JpaRepository

interface HealthCheckRepository : JpaRepository<HealthEntity, Int?>