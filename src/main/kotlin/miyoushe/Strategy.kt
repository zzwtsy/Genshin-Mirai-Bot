package com.github.zzwtsy.miyoushe

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.tools.*
import com.github.zzwtsy.utils.HttpUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.mamoe.mirai.utils.info
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

// 定义数据类并使用 kotlinx.serialization 库进行序列化
@Serializable
data class RoleName(
    val title: String
)

@Serializable
data class RoleList(
    val name: String,
    val list: List<RoleName>
)

@Serializable
@SerialName("children")
data class Children(
    val name: String,
    @SerialName("list")
    val list: List<RoleList>
)

@Serializable
data class GenshinData(
    val data: Data
)

@Serializable
data class Data(
    @SerialName("list")
    val list: List<Children>
)

/**
 * 下载米游社相关资源
 * @author zzwtsy
 * @date 2023/03/23
 * @constructor 创建[Download]
 */
class Download {

    private val format = Json { ignoreUnknownKeys = true }

    /**
     * 下载攻略图
     */
    fun downloadStrategyImage() {
        val roleNameList = getGenshinRoleName()
        val strategyImageUrls = getStrategyImageUrl(
            Tools.roleNameToRegex(roleNameList)
        )
        GenshinMiraiBot.logger.info { "攻略图个数：${strategyImageUrls.size}" }
        HttpUtil.downloadImages(strategyImageUrls, strategyImagePath)
    }

    /**
     * 获取源获取原神所有角色名
     * @return [List<String>] 可用角色名称列表
     */
    private fun getGenshinRoleName(): List<String> {
        val list: ArrayList<String> = arrayListOf()

        val res = HttpUtil.sendGet(roleNamesUrl) ?: return emptyList()
        val genshinData = format.decodeFromString<GenshinData>(res).data

        val roles = genshinData.list.filter { it.name == "图鉴" }
            .flatMap { it.list }
            .filter { it.name == "角色" }
            .flatMap { it.list }
            .map { it.title }
            .filterNot { it.contains("旅行者") }

        list.addAll(roles)
        list.add("旅行者")

        return list
    }

    /**
     * 获取攻略图像 url
     * @param [roleNameRegex] 角色名称所匹配的正则表达式
     * @return [Map<String, String>] 包含角色名称和对应图片链接的 Map
     */
    private fun getStrategyImageUrl(roleNameRegex: Regex): Map<String, String> {
        // 匹配旅行者元素类型的正则表达式
        val travelerRegex = "[草雷水火岩风冰]主".toRegex()

        // 遍历攻略数据，并提取符合条件的角色名称和对应图片链接
        return strategySource.flatMap { e ->
            // 获取攻略数据
            val res = HttpUtil.sendGet("$mysPostsUrl$e") ?: return@flatMap emptyList<Pair<String, String>>()
            val jsonElement = format.parseToJsonElement(res).jsonObject["data"]!!.jsonObject

            // 遍历攻略数据中的帖子，提取角色名称和对应图片链接
            jsonElement["posts"]!!.jsonArray.mapNotNull { post ->
                val imgUrl = post.jsonObject["cover"]!!.jsonObject["url"]!!.jsonPrimitive.content
                val imgName = post.jsonObject["post"]!!.jsonObject["subject"]!!.jsonPrimitive.content

                // 判断角色名称是否符合正则表达式
                val regexMatch = when {
                    travelerRegex.containsMatchIn(imgName) -> travelerRegex.find(imgName)?.value
                    roleNameRegex.containsMatchIn(imgName) -> roleNameRegex.find(imgName)?.value
                    else -> null
                }

                // 如果角色名称符合正则表达式，则将名称和对应图片链接添加到 Map 中
                regexMatch?.let { it to imgUrl }
            }
        }.toMap()
    }

}