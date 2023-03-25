package com.github.zzwtsy.tools

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.dao.RoleDao
import com.github.zzwtsy.utils.SqliteUtils.executeUpdate
import org.yaml.snakeyaml.Yaml
import java.io.File
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
     * 将角色攻略图 md5 与角色别名保存到数据库表
     * @param [aliasesAndMD5s] Map<角色名, 攻略图md5>
     */
    fun saveCharacterAliasesAndMD5s(aliasesAndMD5s: Map<String, String>) {
        val roleAliasesData = getRoleAliasesData()
        if (roleAliasesData.isNullOrEmpty()) {
            GenshinMiraiBot.logger.error("获取角色别名数据失败")
            return
        }
        for ((roleName, imageMd5) in aliasesAndMD5s) {
            roleAliasesData
                .filter { RoleDao.getImageMd5ByName(roleName) == null }
                .filter { it.value.contains(roleName) }
                .forEach { (roleId, aliases) ->
                    for (alias in aliases) {
                        dbUrl.executeUpdate(
                            "INSERT INTO role_aliases (uuid, alias, role_id, image_md5) VALUES ('${getUUID()}', '$alias', $roleId, '$imageMd5')"
                        )
                    }
                }
        }
    }

    /**
     * 获取角色别名数据
     * @return [Map<Int, List<String>>?]
     */
    private fun getRoleAliasesData(): Map<Int, List<String>>? {
        val reader = File("$tempPath/roleName.yml").reader()
        return Yaml().load<Map<Int, List<String>>>(reader)
    }

    /**
     * 获取uuid
     * @return [String] uuid
     */
    private fun getUUID(): String {
        return UUID.randomUUID().toString()
    }
}