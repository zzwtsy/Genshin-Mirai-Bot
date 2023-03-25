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
     * 根据角色名字获取角色攻略图片 md5
     * @param [name] 角色名字
     * @return [Int] 角色攻略图片 md5
     */
    fun getImageMd5ByName(name: String): String? {
        val imageMd5 = dbUrl.executeQuery("SELECT * FROM role_aliases WHERE alias = '$name'") {
            var imageMd5: String? = null
            while (this.next()) {
                imageMd5 = this.getString("image_md5")
            }
            imageMd5
        }

        return imageMd5
    }
}