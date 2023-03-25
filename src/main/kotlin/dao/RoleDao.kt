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
    fun getImageMd5ByName(name: String): Int? {
        val imageMd5 = dbUrl.executeQuery("SELECT * FROM role_aliases WHERE alias = '$name'") {
            var imageMd5 = -1
            while (this.next()) {
                imageMd5 = this.getInt("image_md5")
            }
            imageMd5
        }

        return if (imageMd5 == -1) null else imageMd5
    }
}