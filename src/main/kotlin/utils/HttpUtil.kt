package com.github.zzwtsy.utils

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.tools.MyHeaders
import okhttp3.*
import java.io.File
import java.io.IOException
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

    /**
     * 下载多张图片，并保存到指定路径。
     *
     * @param imageUrls 需要下载的图片 URL 和文件名的映射关系。
     * @param savePath  图片保存路径。
     */
    fun downloadImages(
        imageUrls: Map<String, String>,
        savePath: String,
    ) {
        for ((filename, url) in imageUrls) {
            // 构建请求
            val request = Request.Builder()
                .headers(MyHeaders.baseHeader())
                .url(url)
                .build()
            // 发起异步请求
            client.newCall(request)
                .enqueue(object : Callback {
                    // 请求失败处理
                    override fun onFailure(call: Call, e: IOException) {
                        GenshinMiraiBot.logger.error("「${filename}」下载失败：${e.message}")
                    }

                    // 请求成功处理
                    override fun onResponse(call: Call, response: Response) {
                        // 使用 use 函数管理资源生命周期
                        response.use {
                            GenshinMiraiBot.logger.debug("正在下载：${filename}")
                            if (!response.isSuccessful) {
                                GenshinMiraiBot.logger.error("「${filename}」下载失败")
                                return
                            }
                            // 获取图片类型和数据
                            val contentType = response.body?.contentType()
                            val extension = contentType?.subtype ?: ""
                            val imageData = response.body?.bytes() ?: run {
                                GenshinMiraiBot.logger.error("「${filename}.${extension}」下载失败")
                                return
                            }
                            // 保存图片数据到文件
                            imageData.saveToFile(savePath, filename, extension)
                        }
                    }
                })
            // 延迟 200ms 避免过快下载导致服务器拒绝服务
            Thread.sleep(200)
        }
    }

    /**
     * ByteArray 类型的扩展函数，用于将数据保存到文件中。
     *
     * @param savePath 保存路径。
     * @param filename 文件名。
     * @param extension 文件扩展名。
     */
    private fun ByteArray.saveToFile(savePath: String, filename: String, extension: String) {
        val file = File("$savePath/$filename.$extension")
        // 确保目录存在
        file.parentFile?.mkdirs()
        // 写入数据到文件中
        file.outputStream().use { it.write(this) }
        GenshinMiraiBot.logger.debug("正在保存：${filename}.${extension}")
    }

}