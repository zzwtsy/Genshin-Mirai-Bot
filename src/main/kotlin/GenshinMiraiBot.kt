package com.github.zzwtsy

import com.github.zzwtsy.command.Group
import com.github.zzwtsy.data.pluginConfig.PluginConfig
import com.github.zzwtsy.miyoushe.Strategy
import com.github.zzwtsy.tools.Database
import com.github.zzwtsy.tools.initFilePathsList
import com.github.zzwtsy.tools.strategyImagePath
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.jvm.reloadPluginConfig
import net.mamoe.mirai.utils.info
import java.io.File

object GenshinMiraiBot : KotlinPlugin(
    JvmPluginDescription(
        id = "com.github.zzwtsy.genshin",
        name = "Genshin Mirai Bot",
        version = "0.1.0",
    ) {
        author("zzwtsy")
    }
) {
    override fun onEnable() {
        reloadPluginConfig(PluginConfig)
        //初始化文件夹
        initFilePathsList.forEach {
            val file = File(it)
            if (!file.exists()) {
                file.mkdirs()
                logger.debug("已创建{${it}}文件夹")
            }
        }
        //初始化数据库
        Database.init()
        //下载攻略图
        val file = File(strategyImagePath)
        if (!file.exists() || file.listFiles().isNullOrEmpty()) {
            val status = Strategy().downloadStrategyImage()
            if (status.isNotEmpty())
                logger.info { "没有攻略图的角色${status}" }
        }
        CommandManager.registerCommand(Group)
        logger.info { "Plugin loaded" }
    }
}