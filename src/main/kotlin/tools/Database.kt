package com.github.zzwtsy.tools

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.utils.SqliteUtils.createTable
import com.github.zzwtsy.utils.SqliteUtils.executeUpdate
import org.yaml.snakeyaml.Yaml
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
        Class.forName("org.sqlite.JDBC")
        //创建表
        crateTable()
        //插入角色数据
        insertRoleData()
    }

    /**
     * 创建表
     */
    private fun crateTable() {
        // 创建 characters 表
        dbUrl.createTable("roles") {
            column("id", "INTEGER") {
                primaryKey()
            }
            column("name", "TEXT") {
                notNull()
            }
        }

        // 创建 character_aliases 表
        dbUrl.createTable("role_aliases") {
            column("alias", "TEXT") {
                notNull()
            }
            column("character_id", "INTEGER") {
                notNull()
                foreignKey("character_id", "characters", "id")
            }
        }
    }

    /**
     * 插入角色数据
     */
    private fun insertRoleData() {
        val reader = File("$tempPath/name.yml").reader()
        val data = Yaml().load<Map<Int, List<String>>>(reader)
        val roleList: MutableList<String> = mutableListOf()
        val roleAliasesList: MutableList<String> = mutableListOf()
        data.forEach {
            roleList.add("INSERT INTO roles (id, name) VALUES (${it.key}, '${it.value[0]}')")
            it.value.forEach { e ->
                roleAliasesList.add("INSERT INTO role_aliases (alias, character_id) VALUES ('${e}', ${it.key})")
            }
        }
        roleList.forEach { role ->
            dbUrl.executeUpdate(role)
        }
        roleAliasesList.forEach { role ->
            dbUrl.executeUpdate(role)
        }
    }
}

