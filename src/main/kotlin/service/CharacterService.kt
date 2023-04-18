package com.github.zzwtsy.service

import com.github.zzwtsy.dao.Tables.characters
import com.github.zzwtsy.dao.Tables.insert
import com.github.zzwtsy.tools.DBConnection.dataSource
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.ktorm.entity.map


/**
 * @author zzwtsy
 * @date 2023/03/27
 */
object CharacterService {
    private val database = Database.connect(dataSource).characters

    /**
     * 获取角色名称列表
     * @return [List]
     */
    fun getRoleNames(): List<String> {
        return database.map { it.name }
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
        return database.insert {
            set(it.id, id)
            set(it.name, name)
            set(it.strategyMd5, imageMd5)
        } > 0
    }

    /**
     * 获取所有攻略图 MD5
     * @return [List<String>]
     */
    fun getStrategyMd5s(): List<String> {
        return database.map { it.strategyMd5 }
    }

    /**
     * 更具攻略图 MD5 获取角色名
     * @param [md5] md5
     * @return [String?]
     */
    fun getRoleNameByStrategyMd5(md5: String): String? {
        return database.find { it.strategyMd5 eq md5 }
            ?.name
    }
}