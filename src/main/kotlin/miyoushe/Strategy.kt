package com.github.zzwtsy.miyoushe

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.tools.*
import com.github.zzwtsy.utils.HttpUtil
import data.role.RoleName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.mamoe.mirai.utils.info
import java.io.File

/**
 * 获取米游社攻略相关资源
 * @author zzwtsy
 * @date 2023/03/23
 * @constructor 创建[Strategy]
 */
class Strategy {
    // 匹配旅行者元素类型的正则表达式
    private val travelerRegex by lazy { "[草雷水火岩风冰]主".toRegex() }
    private val format by lazy { Json { ignoreUnknownKeys = true } }

    /**
     * 下载攻略图
     * @return [List<String>] 没有攻略图的角色
     */
    fun downloadStrategyImage(): List<String> {
        val roleNameList = getGenshinRoleName()
        val strategyImageUrls = getStrategyImageUrls(
            Tools.roleNameToRegex(roleNameList)
        )
        GenshinMiraiBot.logger.info { "攻略图个数：${strategyImageUrls.size}" }
        HttpUtil.downloadImages(strategyImageUrls, strategyImagePath)
        (roleNameList as MutableList).removeAll(strategyImageUrls.keys)
        return roleNameList
    }

    /**
     * 更新攻略图
     * @return [List<String>] 没有攻略图的角色
     */
    fun updateStrategyImage(): List<String> {
        val roleNameList = getGenshinRoleName()
        val fileNames = File(strategyImagePath)
            .listFiles()
            .filter { it.isFile }
            .filter { !travelerRegex.containsMatchIn(it.nameWithoutExtension) }
            .map { it.nameWithoutExtension }
        val updateNames = roleNameList
            .filter { it != "旅行者" }
            .filter { !fileNames.contains(it) }
        println(updateNames.isEmpty())
        //无需更新攻略图
        if (updateNames.isEmpty()) return emptyList()
        //需要更新攻略图
        val strategyImageUrls = getStrategyImageUrls(Tools.roleNameToRegex(updateNames))
        GenshinMiraiBot.logger.info { "攻略图个数：${strategyImageUrls.size}" }
        HttpUtil.downloadImages(strategyImageUrls, strategyImagePath)
        return strategyImageUrls.keys
            .filter { !roleNameList.contains(it) }
    }

    /**
     * 获取源获取原神所有角色名
     * @return [List<String>] 可用角色名称列表
     */
    private fun getGenshinRoleName(): List<String> {
        val list: MutableList<String> = mutableListOf()

        val res = HttpUtil.sendGet(roleNamesUrl, MyHeaders.baseHeader()) ?: return emptyList()
        val genshinData = format.decodeFromString<RoleName.GenshinData>(res).data

        val roles = genshinData.list
            .filter { it.name == "图鉴" }
            .flatMap { it.children }
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
    private fun getStrategyImageUrls(roleNameRegex: Regex): Map<String, String> {

        // 获取攻略数据
        val strategyData = strategySource.mapNotNull { id ->
            val res = HttpUtil.sendGet("${mysPostsUrl}${id}", MyHeaders.baseHeader()) ?: return@mapNotNull null
            format.parseToJsonElement(res).jsonObject["data"]!!.jsonObject["posts"]!!.jsonArray
        }.flatten()

        // 遍历攻略数据中的帖子，提取符合条件的角色名称和对应图片链接
        return strategyData.mapNotNull { post ->
            val imgUrl = post.jsonObject["image_list"]!!
                .jsonArray
                .maxByOrNull { it.jsonObject["size"]!!.jsonPrimitive.content.toInt() }
                ?.jsonObject
                ?.get("url")
                ?.jsonPrimitive
                ?.content ?: return@mapNotNull null
            val imgName = post.jsonObject["post"]!!.jsonObject["subject"]!!.jsonPrimitive.content

            // 判断角色名称是否符合正则表达式
            val regexMatch = when {
                travelerRegex.containsMatchIn(imgName) -> travelerRegex.find(imgName)?.value
                roleNameRegex.containsMatchIn(imgName) -> roleNameRegex.find(imgName)?.value
                else -> null
            }

            // 如果角色名称符合正则表达式，则将名称和对应图片链接添加到 Map 中
            regexMatch?.let { it to imgUrl }
        }.toMap()
    }

}