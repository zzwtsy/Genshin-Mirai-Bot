package com.github.zzwtsy.service

import com.github.zzwtsy.dao.Tables.characters
import com.github.zzwtsy.dao.Tables.insert
import com.github.zzwtsy.tools.DBConnection.dataSource
import org.ktorm.database.Database

/**
 * @author zzwtsy
 * @date 2023/03/27
 */
object CharacterService {
    private val database = Database.connect(dataSource)

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