package com.sitehealthcheck

import javax.persistence.*

@Entity
class SiteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    @Column(nullable = false)
    var uri: String?
) {
    constructor() : this(null, null)
}