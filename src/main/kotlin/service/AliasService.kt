package com.github.zzwtsy.service

import com.github.zzwtsy.dao.Tables.aliases
import com.github.zzwtsy.dao.Tables.insert
import com.github.zzwtsy.dao.Tables.joinReferencesAndSelect
import com.github.zzwtsy.models.Character
import com.github.zzwtsy.tools.DBConnection.dataSource
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.find

/**
 * @author zzwtsy
 * @date 2023/03/27
 */
object AliasService {
    private val database = Database.connect(dataSource)

    /**
     * 添加角色别名信息
     * @param [characterId] 角色 id（uuid）
     * @param [alias] 别名
     * @return [Boolean]
     */
    fun add(characterId: String, alias: String): Boolean {
        return database.aliases.insert {
            set(it.name, alias)
            set(it.characterId, characterId)
        } > 0
    }

    /**
     * 通过原神角色别名获取攻略图 md5 值
     * @param [alias] 原神角色别名
     * @return [String?]
     */
    fun getStrategyMd5ByAlias(alias: String): String? {
        return database.aliases
            .joinReferencesAndSelect()
            .find { it.name eq alias }
            ?.characterIdCharacter
            ?.strategyMd5
    }
}