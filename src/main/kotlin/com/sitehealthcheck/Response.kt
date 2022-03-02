package com.sitehealthcheck

import org.springframework.http.HttpStatus

data class Response(val httpStatus: HttpStatus, val responseTime: Long)