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

    @ValueDescription("主指令别名")
    val secondaryNames: Array<String> by value()

    val j by value (
    listOf(
    "神里绫华",
    "Kamisato Ayaka",
    "Ayaka",
    "ayaka",
    "神里",
    "绫华",
    "神里凌华",
    "凌华",
    "白鹭公主",
    "神里大小姐",
    "小乌龟",
    "龟龟"
    )
    )
}