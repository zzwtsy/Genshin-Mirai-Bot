package com.github.zzwtsy.data

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

/**
 * 插件配置文件
 * @author zzwtsy
 * @date 2023/03/23
 */
object PluginConfig : AutoSavePluginConfig("PluginConfig") {
    val groupId by value(0L)
}