package com.github.zzwtsy.tools

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.tools.Tools.getMD5
import com.github.zzwtsy.utils.HttpUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException

/**
 * 下载策攻略图片
 * @author zzwtsy
 * @date 2023/03/25
 */
object DownloadStrategyImage {
    /**
     * 下载多张攻略图片，并保存到指定路径。
     *
     * @param imageUrls 需要下载的攻略图片 URL 和文件名的映射关系。
     * @param savePath  图片保存路径。
     */
    fun downloadImages(
        imageUrls: Map<String, String>,
        savePath: String,
    ) {
        // Map<角色名,角色攻略图片 md5>
        val aliasesAndMD5s = mutableMapOf<String, String>()
        for ((filename, url) in imageUrls) {
            // 构建请求
            val request = Request.Builder()
                .headers(MyHeaders.baseHeader())
                .url(url)
                .build()
            // 发起异步请求
            HttpUtil.getOkhttpClient().newCall(request)
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
                            val imageMd5 = imageData.getMD5()
                            aliasesAndMD5s[filename] = imageMd5
                            // 保存图片数据到文件
                            imageData.saveToFile(savePath, filename, imageMd5, extension)
                            // 将Map<角色名,角色攻略图片 md5> 与角色别名保存到数据库
                            if (aliasesAndMD5s.size == imageUrls.size) {
                                Tools.saveCharacterAliasesAndMD5s(aliasesAndMD5s)
                            }
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
     * @param imageMd5 文件名。
     * @param extension 文件扩展名。
     */
    private fun ByteArray.saveToFile(savePath: String, filename: String, imageMd5: String, extension: String) {
        val file = File("$savePath/$imageMd5.$extension")
        // 确保目录存在
        file.parentFile?.mkdirs()
        // 写入数据到文件中
        file.outputStream().use { it.write(this) }
        GenshinMiraiBot.logger.info("${filename}.${extension}下载完成")
    }
}