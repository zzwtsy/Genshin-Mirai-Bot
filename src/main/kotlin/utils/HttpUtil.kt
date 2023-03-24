package com.github.zzwtsy.utils

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.tools.MyHeaders
import net.mamoe.mirai.utils.info
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
     * 下载图片 (代码来自 ChatGPT)
     * @param [imageUrls] 图像 <图片名，图片 url> 合集
     * @param [savePath] 保存路径
     */
    fun downloadImages(imageUrls: Map<String, String>, savePath: String) {
        for ((filename, url) in imageUrls) {
            val request = Request.Builder()
                .headers(MyHeaders.baseHeader())
                .url(url)
                .build()
            client.newCall(request).enqueue(object : Callback {
                //处理请求失败情况
                override fun onFailure(call: Call, e: IOException) {
                    GenshinMiraiBot.logger.error("「${filename}」下载失败：${e.message}")
                }

                // 处理请求成功情况，response 包含了响应结果等信息
                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) throw Exception("「${filename}」下载失败")
                    //获取图片类型
                    val contentType = response.body?.contentType()
                    val extension = contentType?.subtype ?: ""
                    //获取图片数据
                    val imageData = response.body?.bytes() ?: throw Exception("「${filename}」下载失败")
                    // 保存图片数据到文件
                    imageData.saveToFile(savePath, filename, extension)
                }
            })
            GenshinMiraiBot.logger.info { "正在下载「${filename}」" }
            //避免下载过快导致米游社拒绝下载图片
            Thread.sleep(200)
        }
    }

    /** ByteArray 类型的扩展函数，用于将数据保存到文件中*/
    private fun ByteArray.saveToFile(savePath: String, filename: String, extension: String) {
        val file = File("$savePath/$filename.$extension")
        file.parentFile?.mkdirs()
        file.outputStream().use { it.write(this) }
    }

}