package com.github.zzwtsy.miyoushe.strategy

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.tools.*
import com.github.zzwtsy.utils.HttpUtil
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.mamoe.mirai.utils.info

/**
 * 下载米游社相关资源
 * @author zzwtsy
 * @date 2023/03/23
 * @constructor 创建[Download]
 */
class Download {

    /**
     * 下载攻略图
     */
    fun downloadStrategyImage() {
        val roleNameList = getGenshinRoleName()
        val strategyImageUrls = getStrategyImageUrl(
            Tools.roleNameToRegex(roleNameList), roleNameList.size
        )
        GenshinMiraiBot.logger.info { "攻略图个数：${strategyImageUrls.size}" }
        DownloadImage.downloadImages(strategyImageUrls, strategyImagePath)
    }

    /**
     * 获取源获取原神所有角色名
     * @return [List<String>]
     */
    private fun getGenshinRoleName(): List<String> {
        val list: ArrayList<String> = arrayListOf()
        val res = HttpUtil.sendGet(roleNamesUrl)!!
        val jsonElement = Json.parseToJsonElement(res)
        jsonElement.jsonObject["data"]!!
            .jsonObject["list"]!!
            .jsonArray[0]
            .jsonObject["children"]!!
            .jsonArray[0]
            .jsonObject["list"]!!
            .jsonArray.forEach {
                val content = it.jsonObject["title"]!!.jsonPrimitive.content
                if (!content.contains("旅行者")) {
                    list.add(content)
                }
            }
        list.add("旅行者")
        return list
    }

    /**
     * 获取攻略图像 url
     * @param [roleNameRegex] 角色名正则表达式
     * @param [roleNumber] 角色数量
     * @return [Map<String, String>]
     */
    private fun getStrategyImageUrl(roleNameRegex: Regex, roleNumber: Int): Map<String, String> {
        val map = HashMap<String, String>(roleNumber)
        val travelerRegex = "[草雷水火岩风冰]主".toRegex()
        strategySource.forEach { e ->
            val res = HttpUtil.sendGet("$mysPostsUrl$e")!!
            val jsonElement = Json.parseToJsonElement(res).jsonObject["data"]!!.jsonObject
            jsonElement["posts"]!!.jsonArray.forEach {
                val imgUrl = it.jsonObject["cover"]!!.jsonObject["url"]!!.jsonPrimitive.content
                val imgName = it.jsonObject["post"]!!.jsonObject["subject"]!!.jsonPrimitive.content
                if (roleNameRegex.containsMatchIn(imgName)) {
                    if (travelerRegex.containsMatchIn(imgName)) {
                        val value = travelerRegex.find(imgName)!!.value
                        map[value] = imgUrl
                    } else {
                        val value = roleNameRegex.find(imgName)!!.value
                        map[value] = imgUrl
                    }
                }
            }
        }
        return map
    }
}