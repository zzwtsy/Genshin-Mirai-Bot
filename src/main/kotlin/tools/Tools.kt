package com.github.zzwtsy.tools

import com.github.zzwtsy.service.AliasService
import com.github.zzwtsy.service.CharacterService
import com.github.zzwtsy.service.CharacterService.getStrategyMd5ByAlias
import com.github.zzwtsy.tools.Const.ROLE_NAME_ALIASES_FILE_URL
import com.github.zzwtsy.utils.HttpUtil
import com.github.zzwtsy.utils.JsonUtil
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.mamoe.mirai.utils.MiraiLogger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * 工具类
 * @author zzwtsy
 * @date 2023/03/23
 * @constructor 创建[Tools]
 */
object Tools {
    private val logger = MiraiLogger.Factory.create(this::class, "Tools")

    /**
     * 将角色名转换为正则表达式
     * @param [roleName] 角色名
     * @return [Regex]
     */
    fun roleNameToRegex(roleName: List<String>): Regex {
        val rex = StringBuilder()
        roleName.forEach {
            if (it != roleName.last())
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
    @Throws(NoSuchAlgorithmException::class)
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
        // 匹配游戏中的旅行者主角的正则表达式
        val travelerRegex = Regex("[草雷水火岩风冰]主")

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
            // 如果该角色已有策略的 MD5 值，则跳过该角色
            if (!getStrategyMd5ByAlias(roleName).isNullOrEmpty()) {
                continue
            }

            // 输出正在准备添加的角色名称
            logger.debug("准备添加：$roleName")

            // 获取该角色的所有别名
            val rolesJson = aliasesJson[roleName]?.jsonArray ?: continue

            // 遍历该角色的每个别名，并保存其别名和攻略图 MD5 值
            for (aliasJson in rolesJson) {
                val aliasString = aliasJson.jsonPrimitive.content
                val uuid = getUUID()
                CharacterService.add(uuid, roleName, imageMd5)

                // 如果该别名符合旅行者主角的正则表达式，则将其设置为该角色的别名
                val addSuccess = AliasService.add(
                    uuid,
                    if (travelerRegex.containsMatchIn(aliasString)) roleName else aliasString
                )

                // 输出已添加的别名和攻略图 MD5 值
                if (addSuccess) {
                    logger.debug("$roleName 已添加别名 $aliasString")
                }
            }
        }

//        for ((roleName, imageMd5) in aliasesAndMD5s) {
//            logger.debug("准备添加：$roleName")
//            CharacterService.getStrategyMd5ByAlias(roleName) ?: run {
//                roleAliasesData
//                    .filter { it.value.contains(roleName) }
//                    .forEach { (_, aliases) ->
//                        logger.debug("正在添加：$roleName")
//                        val uuid = getUUID()
//                        CharacterService.add(uuid, roleName, imageMd5)
//                        if (travelerRegex.containsMatchIn(roleName)) {
//                            val add = AliasService.add(uuid, roleName)
//                            if (add) logger.debug("${roleName}已添加别名${roleName}")
//                        } else {
//                            for (alias in aliases) {
//                                val add = AliasService.add(uuid, alias)
//                                if (add) logger.debug("${roleName}已添加别名${alias}")
//                            }
//                        }
//                    }
//            }
//        }
    }

    /**
     * 获取角色别名数据
     * @return [Map<Int, List<String>>?]
     */
    private fun getRoleAliasesData(): String? {
        return HttpUtil.sendGet(ROLE_NAME_ALIASES_FILE_URL)
    }

    /**
     * 获取uuid
     * @return [String] uuid
     */
    private fun getUUID(): String {
        return UUID.randomUUID().toString()
    }
}