package com.sitehealthcheck.core

import com.sitehealthcheck.Response
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.StopWatch
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class HealthCheckService {

    fun check(url: String): Response {
        val client = HttpClient.newBuilder().build()
        val httpRequest = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build()
        val stopWatch = StopWatch().also { it.start() }
        val response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString())
        return Response(
            HttpStatus.valueOf(response.statusCode()),
            with(stopWatch) { stop(); lastTaskTimeMillis }
        )
    }

}