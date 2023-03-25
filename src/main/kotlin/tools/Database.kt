package com.github.zzwtsy.tools

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.utils.HttpUtil
import com.github.zzwtsy.utils.SqliteUtils.createTable
import java.io.File

/**
 * 数据库工具合集
 * @author zzwtsy
 * @date 2023/03/25
 */
object Database {
    /**
     * 初始化数据库
     */
    fun init() {
        if (File(dbPath).exists()) {
            GenshinMiraiBot.logger.info("数据库文件已存在")
            return
        }
        //注册数据库
        Class.forName("org.sqlite.JDBC")
        //创建表
        crateTable()
        //下载角色别名数据
        val roleName = File(roleNameAliasesPath)
        if (!roleName.exists()) {
            val sendGet = HttpUtil.sendGet(roleNameAliasesFileUrl)
            val byteArray = sendGet?.encodeToByteArray()
            roleName.outputStream().use {
                byteArray?.let { it1 -> it.write(it1) }
            }
        }
    }

    /**
     * 创建表
     */
    private fun crateTable() {

        // 创建 role_aliases 表
        dbUrl.createTable("role_aliases") {
            column("uuid", "TEXT") {
                notNull()
                primaryKey()
            }
            column("alias", "TEXT") {
                notNull()
            }
            column("role_id", "INTEGER") {
                notNull()
            }
            column("image_md5", "TEXT") {
                notNull()
            }
        }
    }

}

