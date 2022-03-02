package com.sitehealthcheck.repository

import com.sitehealthcheck.SiteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SiteRepository : JpaRepository<SiteEntity, Int?>