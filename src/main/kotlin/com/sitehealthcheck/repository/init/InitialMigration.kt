package com.sitehealthcheck.repository.init

import com.sitehealthcheck.SiteEntity
import com.sitehealthcheck.repository.SiteRepository
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class InitialMigration(
    private val siteRepository: SiteRepository
) {

    @PostConstruct
    fun populateDb() {
        siteRepository.saveAll(
            listOf(
                SiteEntity().apply { uri = "https://www.google.com" },
                SiteEntity().apply { uri = "https://www.facebook.com" },
                SiteEntity().apply { uri = "https://www.ok.ru" },
                SiteEntity().apply { uri = "https://www.vk.com" }
            )
        )
    }
}