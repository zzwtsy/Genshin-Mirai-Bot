package com.github.zzwtsy.tools

import com.github.zzwtsy.data.pluginConfig.PluginRegexConfig
import com.github.zzwtsy.service.AliasService
import com.github.zzwtsy.service.CharacterService
import com.github.zzwtsy.service.AliasService.getStrategyMd5ByAlias
import com.github.zzwtsy.tools.Const.ROLE_NAME_ALIASES_FILE_URL
import com.github.zzwtsy.tools.Const.STRATEGY_IMAGE_PATH
import com.github.zzwtsy.utils.HttpUtil
import com.github.zzwtsy.utils.JsonUtil
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.mamoe.mirai.utils.MiraiLogger
import java.io.File
import java.security.MessageDigest
import java.util.*

/**
 * 工具类
 * @author zzwtsy
 * @date 2023/03/23
 * @constructor 创建[Tools]
 */
object Tools {
    private val logger = MiraiLogger.Factory.create(this::class, "Genshin Mirai Bot-Tools")

    /**
     * 将角色名转换为正则表达式
     * @return [Regex]
     */
    fun List<String>.roleNameToRegex(): Regex {
        val rex = StringBuilder()
        this.forEach {
            if (it != this.last())
                rex.append("($it)|")
            else
                rex.append("($it)")
        }
        return "$rex".toRegex()
    }

    /**
     * 计算给定字节数组的 MD5 散列值，并返回结果作为十六进制字符串。
     *
     * @return 字节数组的 MD5 散列值，作为十六进制字符串。
     */
    fun ByteArray.getMD5(): String {
        // 创建 MessageDigest 实例并计算散列值
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(this)

        // 将计算得到的散列值转换为十六进制字符串并返回
        return digest.joinToString("") { "%02x".format(it) }
    }

    /**
     * 将角色攻略图 MD5 与角色别名保存到数据库表
     * @param [aliasesAndMD5s] Map<角色名, 攻略图 MD5>
     */
    fun saveCharacterAliasesAndMD5s(aliasesAndMD5s: Map<String, String>) {
        // 正则表达式：匹配旅行者
        val travelerRegex = PluginRegexConfig.travelerElementTypeRegex.toRegex()

        // 获取角色别名数据
        val roleAliasesData = getRoleAliasesData()

        // 检查是否获取到了角色别名数据
        if (roleAliasesData == null) {
            logger.error("获取角色别名数据失败")
            return
        }

        // 将 JSON 字符串转换为 JsonObject 对象
        val aliasesJson = JsonUtil.fromJson(roleAliasesData)?.jsonObject ?: return

        // 遍历每个角色的别名和攻略图 MD5 值
        for ((roleName, imageMd5) in aliasesAndMD5s) {
            // 如果该角色已有攻略的 MD5 值，则跳过该角色
            if (!getStrategyMd5ByAlias(roleName).isNullOrEmpty()) {
                continue
            }

            // 输出正在准备添加的角色名称
            logger.debug("准备添加：$roleName")

            // 生成 UUID
            val uuid = getUUID()

            // 添加角色
            val addCharacterSuccess = CharacterService.add(uuid, roleName, imageMd5)
            if (!addCharacterSuccess) {
                logger.error("添加角色 $roleName 失败")
                continue
            }

            // 如果是旅行者，则别名为自己（风主。。。）
            if (travelerRegex.containsMatchIn(roleName)) {
                val addAliasSuccess = AliasService.add(uuid, roleName)
                if (!addAliasSuccess) {
                    logger.error("添加角色别名 $roleName 失败")
                } else {
                    logger.debug("$roleName 已添加别名 $roleName")
                }
                continue
            }

            // 获取该角色的所有别名
            val rolesJson = aliasesJson[roleName]?.jsonArray ?: continue

            // 如果没有别名，直接添加角色名称作为别名
            if (rolesJson.isEmpty()) {
                val addAliasSuccess = AliasService.add(uuid, roleName)
                if (!addAliasSuccess) {
                    logger.error("添加角色别名 $roleName 失败")
                } else {
                    logger.debug("$roleName 已添加别名 $roleName")
                }
                continue
            }

            // 遍历该角色的每个别名，并保存其别名和攻略图 MD5 值
            for (aliasJson in rolesJson) {
                val aliasString = aliasJson.jsonPrimitive.content

                val addAliasSuccess = AliasService.add(uuid, aliasString)

                // 输出已添加的别名和攻略图 MD5 值
                if (addAliasSuccess) {
                    logger.debug("$roleName 已添加别名 $aliasString")
                } else {
                    logger.error("添加角色别名 $aliasString 失败")
                }
            }
        }
        // 输出完成信息
        logger.info("角色别名数据添加完成")
    }

    /**
     * 获取角色别名数据
     * @return [Map<Int, List<String>>?]
     */
    fun getRoleAliasesData(): String? {
        return HttpUtil.sendGet(ROLE_NAME_ALIASES_FILE_URL)
    }

    /**
     * 获取uuid
     * @return [String] uuid
     */
    fun getUUID(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * 获取本地攻略图文件夹中所有攻略图文件名（无后缀名）
     * @return [List<String>?]
     */
    fun getStrategyFileNames(): List<String>? {
        return File(STRATEGY_IMAGE_PATH)
            .list()
            ?.map { it.split(".")[0] }
    }

    /**
     * 格式消息
     * @return [String]
     */
    fun List<String>.formatMessage(): String {
        val builder = StringBuilder()
        this.forEach {
            builder.append(it)
        }
        return builder.append("\n").toString()
    }
}