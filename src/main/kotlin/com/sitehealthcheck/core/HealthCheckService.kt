package com.sitehealthcheck.core

import com.sitehealthcheck.Response
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.StopWatch
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpConnectTimeoutException
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

@Service
class HealthCheckService {

    fun check(url: String): Response {
        val client = HttpClient.newHttpClient()
        val stopWatch = StopWatch().also { it.start() }
        return try {
            val httpRequest = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .build()
            val response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString())
            Response(
                HttpStatus.valueOf(response.statusCode()),
                stopWatch.run { stop(); lastTaskTimeMillis }
            )
        } catch (e: HttpConnectTimeoutException) {
            Response(HttpStatus.REQUEST_TIMEOUT, -1)
        }

    }

}