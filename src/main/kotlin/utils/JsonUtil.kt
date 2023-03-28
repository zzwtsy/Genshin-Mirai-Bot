package com.github.zzwtsy.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

/**
 * json 工具类
 * @author zzwtsy
 * @date 2023/03/28
 */
object JsonUtil {
    /**
     * 公共的 JSON 序列化/反序列化对象
     */
    val json = Json { ignoreUnknownKeys = true }

    /**
     * 将 JSON 字符串解码为指定类型的 Kotlin 对象。
     * @param jsonString 要解码的 JSON 字符串
     * @return 解码后的 Kotlin 对象，如果解码失败则返回 null
     */
    inline fun <reified T> fromJson(jsonString: String?): T? {
        return try {
            json.decodeFromString(jsonString ?: "")
        } catch (e: Throwable) {
            null
        }
    }

    /**
     * 将 JSON 字符串解析为 JsonElement。
     *
     * @param jsonString 要解析的 JSON 字符串
     * @return 解析后的 JsonElement，如果解析失败则返回 null
     */
    fun fromJson(jsonString: String?): JsonElement? {
        return try {
            json.parseToJsonElement(jsonString ?: "")
        } catch (e: Throwable) {
            null
        }
    }

    /**
     * 将 Kotlin 对象编码为 JSON 字符串。
     * @param src 要编码的 Kotlin 对象
     * @return 编码后的 JSON 字符串
     */
    inline fun <reified T> toJson(src: T): String {
        return json.encodeToString(src)
    }
}