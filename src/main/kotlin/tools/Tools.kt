package com.github.zzwtsy.tools

/**
 * 工具类
 * @author zzwtsy
 * @date 2023/03/23
 * @constructor 创建[Tools]
 */
object Tools {
    /**
     * 将角色名转换为正则表达式
     * @param [roleName] 角色名
     * @return [Regex]
     */
    fun roleNameToRegex(roleName: List<String>): Regex {
        val rex = StringBuilder()
        roleName.forEach {
            if (it != roleName.last())
                rex.append("($it)|")
            else
                rex.append("($it)")
        }
        return "$rex".toRegex()
    }
}