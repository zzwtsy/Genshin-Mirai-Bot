package com.github.zzwtsy.dao

import com.github.zzwtsy.tools.dbUrl
import com.github.zzwtsy.utils.SqliteUtils.executeQuery

/**
 * 原神角色表
 * @author zzwtsy
 * @date 2023/03/25
 */
object RoleDao {
    /**
     * 根据角色名字获取角色 id
     * @param [name] 名字
     * @return [Int] 角色 id
     */
    fun getRoleIdByName(name: String): Int {
        val roleId = dbUrl.executeQuery("ELECT * FROM character_aliases WHERE alias = '$name'") {
            var roleId = 0
            while (this.next()) {
                roleId = this.getInt("character_id")
            }
            roleId
        }

        return roleId
    }
}