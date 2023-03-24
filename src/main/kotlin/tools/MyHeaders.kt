package com.github.zzwtsy.tools

import okhttp3.Headers
import kotlin.random.Random

object MyHeaders {
    private val userAgents = listOf(
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.5410.0 Safari/537.36",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.5410.0 Safari/537.36",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36 Edg/111.0.1661.51",
        "Mozilla/5.0 (Windows NT 10; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
    )

    fun baseHeader(): Headers {
        return Headers.Builder()
            .add("user-agent: ${userAgents[Random.nextInt(userAgents.size)]}")
            .build()
    }
}