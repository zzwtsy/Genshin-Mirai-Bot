package com.github.zzwtsy.utils

import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.util.concurrent.TimeUnit

/**
 * http工具类
 * @author zzwtsy
 * @date 2023/02/07
 * @constructor 创建[HttpUtil]
 */
object HttpUtil {
    private val client = OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS).build()

    /**
     * 获取 Okhttp Client
     * @return [OkHttpClient]
     */
    fun getOkhttpClient(): OkHttpClient {
        return client
    }

    /**
     * 发送 get 请求
     * @param [url] url
     * @param [headers] 请求头
     * @return [String?]
     */
    fun sendGet(url: String, headers: Headers): String? {
        val request = Request.Builder()
            .get()
            .url(url)
            .headers(headers)
            .build()
        return client.newCall(request).execute().body?.string()
    }

    /**
     * 发送 get 请求
     * @param [url] url
     * @return [String?]
     */
    fun sendGet(url: String): String? {
        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        return client.newCall(request).execute().body?.string()
    }

    /**
     * 发送 post 请求
     * @param [url] url
     * @param [headers] 请求头
     * @param [body] 请求体
     * @return [String?]
     */
    fun sendPost(url: String, headers: Headers, body: RequestBody): String? {
        val request = Request.Builder()
            .post(body)
            .url(url)
            .headers(headers)
            .build()
        return client.newCall(request).execute().body?.string()
    }

}