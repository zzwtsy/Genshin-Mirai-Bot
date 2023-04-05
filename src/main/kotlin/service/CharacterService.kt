package com.github.zzwtsy.service

import com.github.zzwtsy.dao.Tables.characters
import com.github.zzwtsy.dao.Tables.insert
import com.github.zzwtsy.dao.Tables.joinReferencesAndSelect
import com.github.zzwtsy.tools.DBConnection.dataSource
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.map

/**
 * @author zzwtsy
 * @date 2023/03/27
 */
object CharacterService {
    private val database = Database.connect(dataSource)

    /**
     * 通过原神角色别名获取攻略图 md5 值
     * @param [alias] 原神角色别名
     * @return [String?]
     */
    fun getStrategyMd5ByAlias(alias: String): String? {
        return database.characters
            .joinReferencesAndSelect()
            .filter { it.name eq alias }
            .map { it.strategyMd5 }
            .singleOrNull()
    }

    /**
     * 获取角色名称列表
     * @return [List]
     */
    fun getRoleNames(): List<String> {
        return database.characters.map { it.name }
    }

    /**
     * 获取攻略图 MD5 列表
     * @return [List<String>]
     */
    fun getStrategyImageMd5s(): List<String> {
        return database.characters
            .map { it.strategyMd5 }
    }

    /**
     * 添加角色信息
     * @param [id] id
     * @param [name] 名字
     * @param [imageMd5] 攻略图 md5
     * @return [Boolean]
     */
    fun add(id: String, name: String, imageMd5: String): Boolean {
        return database.characters.insert {
            set(it.id, id)
            set(it.name, name)
            set(it.strategyMd5, imageMd5)
        } > 0
    }
}