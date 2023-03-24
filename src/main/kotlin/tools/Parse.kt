package com.github.zzwtsy.tools

import com.github.zzwtsy.data.role.RoleNameConfig

object Parse {
    /**
     * 解析角色名
     * @param [roleName] 角色名
     */
    fun parseRoleName(roleName: String) {
        val strings = RoleNameConfig.traveler["旅行者"]
        strings?.contains(roleName)
        RoleNameConfig
    }
}