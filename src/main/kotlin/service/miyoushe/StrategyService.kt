package com.github.zzwtsy.service.miyoushe

import com.github.zzwtsy.data.pluginConfig.PluginRegexConfig
import com.github.zzwtsy.data.role.RoleName
import com.github.zzwtsy.service.CharacterService
import com.github.zzwtsy.tools.Const.MYS_POSTS_URL
import com.github.zzwtsy.tools.Const.ROLE_NAMES_URL
import com.github.zzwtsy.tools.Const.STRATEGY_IMAGE_PATH
import com.github.zzwtsy.tools.Const.STRATEGY_SOURCE
import com.github.zzwtsy.tools.Const.oss
import com.github.zzwtsy.tools.DownloadImage
import com.github.zzwtsy.tools.MyHeaders
import com.github.zzwtsy.tools.Tools.roleNameToRegex
import com.github.zzwtsy.utils.HttpUtil
import com.github.zzwtsy.utils.JsonUtil
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.mamoe.mirai.utils.MiraiLogger
import net.mamoe.mirai.utils.info
import java.io.File

/**
 * 获取米游社攻略相关资源
 * @author zzwtsy
 * @date 2023/03/23
 * @constructor 创建[StrategyService]
 */
class StrategyService {
    private val logger = MiraiLogger.Factory.create(this::class, "StrategyService")

    // 匹配旅行者元素类型的正则表达式
    private val travelerRegex = PluginRegexConfig.travelerElementTypeRegex.toRegex()

    /**
     * 下载攻略图
     * @return [List<String>] 没有攻略图的角色
     */
    fun downloadStrategyImage(): List<String> {
        // 获取原神所有角色名称
        val roleNameList = getGenshinRoleName()

        // 获取符合条件的攻略图片链接
        val strategyImageUrls = getStrategyImageUrls(
            roleNameList.roleNameToRegex()
        )

        // 记录符合条件的攻略图片链接数量
        logger.info { "攻略图个数：${strategyImageUrls.size}" }

        // 下载符合条件的攻略图片
        DownloadImage.downloadStrategyImage(strategyImageUrls, STRATEGY_IMAGE_PATH)

        // 过滤旅行者和已经获取的角色，只保留没有攻略图的角色名称列表
        return strategyImageUrls
            .filter { !travelerRegex.containsMatchIn(it.key) }
            .filter { !roleNameList.contains(it.key) }
            .map { it.key }

    }

    /**
     * 更新攻略图
     * @return [List<String>] 已更新攻略图的角色
     */
    fun updateStrategyImage(): List<String> {
        //TODO: 有问题需要改进

        // 获取原神所有角色名称
        val roleNameList = getGenshinRoleName()

        // 获取已经下载的攻略图片名称列表
        val fileNames = CharacterService.getRoleNames()

        // 攻略图文件不存在或无攻略图片，返回所有角色名称
        if (fileNames.isEmpty()) return roleNameList

        // 筛选出需要更新的攻略图片名称列表
        val updateNames = roleNameList
            .filter { it != "旅行者" }
            .filter { !fileNames.contains(it) } // 只保留未下载的角色

        // 如果不需要更新，则返回空列表
        if (updateNames.isEmpty()) return emptyList()

        // 获取需要更新的攻略图片链接
        val strategyImageUrls = getStrategyImageUrls(updateNames.roleNameToRegex())

        // 记录符合条件的攻略图片链接数量
        logger.info { "攻略图个数：${strategyImageUrls.size}" }

        // 下载需要更新的攻略图片
        DownloadImage.downloadStrategyImage(strategyImageUrls, STRATEGY_IMAGE_PATH)

        // 已经获取攻略图的角色，只保留没有攻略图的角色名称列表
        return strategyImageUrls.keys
            .filter { roleNameList.contains(it) }

    }

    /**
     * 获取源获取原神所有角色名
     * @return [List<String>] 可用角色名称列表
     */
    private fun getGenshinRoleName(): List<String> {
        // 创建列表，用于存储所有角色名称
        val list: MutableList<String> = mutableListOf()

        // 获取原神角色名称数据
        val res = HttpUtil.sendGet(ROLE_NAMES_URL, MyHeaders.baseHeader()) ?: return emptyList()
        val genshinData = JsonUtil.fromJson<RoleName.GenshinData>(res)?.data ?: return emptyList()

        // 筛选出所有角色名称
        val roles = genshinData.list
            .filter { it.name == "图鉴" } // 筛选出图鉴数据
            .flatMap { it.children } // 扁平化处理所有子目录
            .filter { it.name == "角色" } // 筛选出角色目录
            .flatMap { it.list } // 扁平化处理所有角色数据
            .map { it.title } // 提取角色名称
            .filterNot { it.contains("旅行者") } // 过滤旅行者角色

        // 添加所有角色名称到列表中
        list.addAll(roles)

        // 添加旅行者角色名称到列表中
        list.add("旅行者")

        // 返回角色名称列表
        return list
    }

    /**
     * 获取攻略图像 url
     * @param [roleNameRegex] 角色名称所匹配的正则表达式
     * @return [Map<String, String>] 包含角色名称和对应图片链接的 Map
     */
    private fun getStrategyImageUrls(roleNameRegex: Regex): Map<String, String> {

        // 获取攻略数据
        val strategyData = STRATEGY_SOURCE.mapNotNull { id ->
            val res = HttpUtil.sendGet("${MYS_POSTS_URL}${id}", MyHeaders.baseHeader()) ?: return@mapNotNull null
            JsonUtil.fromJson(res)?.jsonObject?.get("data")?.jsonObject?.get("posts")?.jsonArray
        }.flatten()

        // 遍历攻略数据中的帖子，提取符合条件的角色名称和对应图片链接
        return strategyData.mapNotNull { post ->
            val imgUrl = post.jsonObject["image_list"]
                ?.jsonArray
                ?.maxByOrNull {
                    it.jsonObject["size"]?.jsonPrimitive?.content?.toInt() ?: return@mapNotNull null
                }
                ?.jsonObject
                ?.get("url")
                ?.jsonPrimitive
                ?.content ?: return@mapNotNull null
            val imgName = post.jsonObject["post"]?.jsonObject?.get("subject")?.jsonPrimitive?.content ?: ""

            // 判断角色名称是否符合正则表达式
            val regexMatch = when {
                travelerRegex.containsMatchIn(imgName) -> travelerRegex.find(imgName)?.value
                roleNameRegex.containsMatchIn(imgName) -> roleNameRegex.find(imgName)?.value
                else -> null
            }

            // 如果角色名称符合正则表达式，则将名称和对应图片链接添加到 Map 中
            regexMatch?.let { it to "$imgUrl$oss" }
        }.toMap()
    }
}