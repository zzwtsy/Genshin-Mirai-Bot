package com.github.zzwtsy.tools

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.tools.Const.DB_FILE_PATH
import com.github.zzwtsy.tools.Const.ROLE_NAME_ALIASES_FILE_URL
import com.github.zzwtsy.tools.Const.ROLE_NAME_ALIASES_PATH
import com.github.zzwtsy.utils.HttpUtil
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
        if (File(DB_FILE_PATH).exists()) {
            GenshinMiraiBot.logger.info("数据库文件已存在")
            return
        }
        //创建表
        crateTable()
        //下载角色别名数据
        val roleName = File(ROLE_NAME_ALIASES_PATH)
        if (!roleName.exists()) {
            val sendGet = HttpUtil.sendGet(ROLE_NAME_ALIASES_FILE_URL)
            val byteArray = sendGet?.encodeToByteArray()
            roleName.outputStream().use {
                byteArray?.let { content -> it.write(content) }
            }
        }
    }

    /**
     * 创建表
     */
    private fun crateTable() {

        // 创建 Characters 表
        val connection = dataSource.connection
        connection.prepareStatement(
            """
            CREATE TABLE "Characters" (
              "id" text,
              "name" text NOT NULL,
              "strategy_md5" TEXT NOT NULL,
              PRIMARY KEY ("id")
            );
        """.trimIndent()
        )
        // 创建 Aliases 表
        connection.prepareStatement(
            """
            CREATE TABLE "Aliases" (
              "character_id" text NOT NULL,
              "name" text NOT NULL,
              PRIMARY KEY ("character_id", "name"),
              FOREIGN KEY ("character_id") REFERENCES "Characters" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
            );
        """.trimIndent()
        )
    }
}

