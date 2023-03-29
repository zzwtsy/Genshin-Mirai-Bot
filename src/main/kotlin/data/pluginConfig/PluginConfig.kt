package com.github.zzwtsy.data.pluginConfig

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * 插件配置文件
 * @author zzwtsy
 * @date 2023/03/23
 */
object PluginConfig : AutoSavePluginConfig("PluginConfig") {

    @ValueDescription("qq 群号")
    val groupId by value(0L)

    @ValueDescription("群聊主指令别名")
    val groupSecondaryNames: Array<String> by value(arrayOf("原神", "genshin"))

    @ValueDescription("管理员主指令别名")
    val adminSecondaryNames: Array<String> by value(arrayOf("管理员", "admin"))
}