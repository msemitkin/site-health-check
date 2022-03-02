package com.sitehealthcheck.core

import com.sitehealthcheck.SiteEntity
import com.sitehealthcheck.repository.SiteRepository
import org.springframework.stereotype.Service

@Service
class AddSiteService(
    private val siteRepository: SiteRepository
) {

    fun addHealthCheck(uri: String) {
        val siteEntity = SiteEntity()
            .apply { this.uri = uri }
        siteRepository.save(siteEntity)
    }

}