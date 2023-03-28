package com.github.zzwtsy.tools

import com.github.zzwtsy.tools.Tools.getMD5
import com.github.zzwtsy.utils.HttpUtil
import kotlinx.coroutines.*
import net.mamoe.mirai.utils.MiraiLogger
import okhttp3.Request
import java.io.File

/**
 * 下载策攻略图片
 * @author zzwtsy
 * @date 2023/03/25
 */
object DownloadImage {
    private val logger = MiraiLogger.Factory.create(this::class, "Genshin Mirai Bot-DownloadImage")

    /**
     * 下载多张攻略图片，并保存到指定路径。
     *
     * @param imageUrls 需要下载的攻略图片 URL 和文件名的映射关系。
     * @param savePath  图片保存路径。
     */
    fun downloadStrategyImage(
        imageUrls: Map<String, String>,
        savePath: String,
    ) {
        // Map<角色名,角色攻略图片 md5>
        val aliasesAndMD5s = mutableMapOf<String, String>()
        val client = HttpUtil.getOkhttpClient()
        CoroutineScope(Dispatchers.IO).launch {
            for ((filename, url) in imageUrls) {
                // 构建请求
                val request = Request.Builder()
                    .headers(MyHeaders.baseHeader())
                    .url(url)
                    .build()
                // 发起异步请求
                try {
                    val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
                    logger.debug("正在下载：${filename}")
                    if (!response.isSuccessful) {
                        logger.error("「${filename}」下载失败")
                        continue
                    }
                    // 获取图片类型和数据
                    val contentType = response.body?.contentType()?.subtype ?: ""
                    val imageData = response.body?.byteStream()?.readBytes()
                    if (imageData == null) {
                        logger.error("「${filename}.${contentType}」下载失败")
                        continue
                    }
                    val imageMd5 = imageData.getMD5()
                    aliasesAndMD5s[filename] = imageMd5
                    // 保存图片数据到文件
                    imageData.saveToFile(savePath, filename, imageMd5, contentType)
                } catch (e: Exception) {
                    logger.error("「${filename}」下载失败：${e.message}")
                }
                // 延迟 200ms 避免过快下载导致服务器拒绝服务
                delay(200)
            }
        }.invokeOnCompletion {
            when (it) {
                null -> {
                    logger.info("原神攻略图下载完成")
                    // 将Map<角色名,角色攻略图片 md5> 与角色别名保存到数据库
                    Tools.saveCharacterAliasesAndMD5s(aliasesAndMD5s)
                }

                is CancellationException -> {
                    logger.info("攻略图下载被取消")
                }

                else -> {
                    logger.error("攻略图下载失败：$it")
                }
            }
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
        logger.info("${filename}.${extension}下载完成")
    }
}