package com.github.zzwtsy.tools

import com.github.zzwtsy.GenshinMiraiBot
import net.mamoe.mirai.utils.info
import okhttp3.*
import java.io.File
import java.io.IOException

private val okHttpClient = OkHttpClient()

/**
 * 下载图片
 * @author zzwtsy
 * @date 2023/03/23
 */
object DownloadImage {
    /**
     * 下载图片 (代码来自 ChatGPT)
     * @param [imageUrls] 图像 <图片名，图片 url> 合集
     * @param [savePath] 保存路径
     */
    fun downloadImages(imageUrls: Map<String, String>, savePath: String) {
        for ((filename, url) in imageUrls) {
            val request = Request.Builder().url(url).build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                //处理请求失败情况
                override fun onFailure(call: Call, e: IOException) {
                    GenshinMiraiBot.logger.error("「${filename}」下载失败", e)
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
                    imageData.saveToFile("$savePath/$filename.$extension")
                }
            })
            GenshinMiraiBot.logger.info { "正在下载「${filename}」" }
            //避免下载过快导致米游社拒绝下载图片
            Thread.sleep(200)
        }
    }

    /** ByteArray 类型的扩展函数，用于将数据保存到文件中*/
    private fun ByteArray.saveToFile(path: String) {
        val file = File(path)
        file.parentFile?.mkdirs()
        file.outputStream().use { it.write(this) }
    }
}
