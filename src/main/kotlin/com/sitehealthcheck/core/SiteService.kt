package com.sitehealthcheck.core

import com.sitehealthcheck.SiteEntity
import com.sitehealthcheck.repository.SiteRepository
import org.springframework.stereotype.Service

@Service
class SiteService(private val siteRepository: SiteRepository) {

    fun saveSite(uri: String) {
        val siteEntity = SiteEntity().apply { this.uri = uri }
        siteRepository.save(siteEntity)
    }

    fun getAllSites(): List<SiteEntity> = siteRepository.findAll()

}