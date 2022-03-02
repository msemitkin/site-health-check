package com.sitehealthcheck

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class HealthEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    @Column(nullable = false)
    var uri: String?,

    @Column(nullable = false)
    var healthCheckTime: LocalDateTime?,

    @Column(nullable = false)
    var responseTime: Long?,

    @Column(nullable = false)
    var responseStatusCode: Int?

) {
    constructor() : this(null, null, null, null, null)
}
