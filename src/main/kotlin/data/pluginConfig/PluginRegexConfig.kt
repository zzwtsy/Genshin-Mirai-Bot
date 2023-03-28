package com.github.zzwtsy.data.pluginConfig

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * 插件正则表达式配置
 * @author zzwtsy
 * @date 2023/03/28
 */
object PluginRegexConfig : AutoSavePluginConfig("PluginRegexConfig") {
    @ValueDescription("旅行者元素类型正则表达式")
    var travelerElementTypeRegex by value("[草雷水火岩风冰]主")

    @ValueDescription("角色名位于元素类型前")
    var roleElementTypeRegex by value(".*[草雷水火岩风冰]")

    @ValueDescription("元素类型位于角色名前")
    var elementTypeRoleRegex by value("[草雷水火岩风冰].*")

    @ValueDescription("旅行者别名正则表达式")
    var travelersAliasRegex by value("(主角)|(旅行者)|(卑鄙的外乡人)|(荣誉骑士)|(爷)|(履刑者)|(抽卡不歪真君)|(荧)|(女主)|(女主角)|(莹)|(萤)|(黄毛阿姨)|(荧妹)|(女爷)|(空)|(男主)|(男主角)|(龙哥)|(空哥)|(男爷)")
}