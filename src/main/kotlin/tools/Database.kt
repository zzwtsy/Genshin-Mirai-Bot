package com.github.zzwtsy.tools

import com.github.zzwtsy.tools.Const.DB_FILE_PATH
import com.github.zzwtsy.tools.DBConnection.dataSource
import net.mamoe.mirai.utils.MiraiLogger
import java.io.File

/**
 * 数据库工具合集
 * @author zzwtsy
 * @date 2023/03/25
 */
object Database {
    private val logger = MiraiLogger.Factory.create(this::class, "Genshin Mirai Bot - Database")

    /**
     * 初始化数据库
     */
    fun init() {
        if (File(DB_FILE_PATH).exists()) {
            logger.info("数据库文件已存在")
            return
        }
        //创建表
        crateTable()
    }

    /**
     * 创建表
     */
    private fun crateTable() {

        logger.info("创建 Characters 表")
        // 创建 Characters 表
        val connection = dataSource.connection
        val charactersStatus = connection.prepareStatement(
            """
            CREATE TABLE "Characters" (
              "id" UUID,
              "name" TEXT NOT NULL,
              "strategy_md5" TEXT NOT NULL,
              PRIMARY KEY ("id")
            );
        """.trimIndent()
        ).executeUpdate() == 0

        if (charactersStatus)
            logger.info("成功创建 Characters 表")
        else
            logger.error("创建 Characters 表失败")

        logger.info("创建 Aliases 表")
        // 创建 Aliases 表
        val aliasesStatus = connection.prepareStatement(
            """
            CREATE TABLE "Aliases" (
              "character_id" UUID NOT NULL,
              "name" TEXT NULL,
              PRIMARY KEY ("character_id", "name"),
              FOREIGN KEY ("character_id") REFERENCES "Characters" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
            );
        """.trimIndent()
        ).executeUpdate() == 0
        if (aliasesStatus)
            logger.info("成功创建 Aliases 表")
        else
            logger.error("创建 Aliases 表失败")
    }
}

