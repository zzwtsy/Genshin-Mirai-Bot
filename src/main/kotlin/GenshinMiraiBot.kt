package com.github.zzwtsy

import com.github.zzwtsy.command.Group
import com.github.zzwtsy.data.pluginConfig.PluginConfig
import com.github.zzwtsy.service.miyoushe.StrategyService
import com.github.zzwtsy.tools.Const.INIT_FILE_PATHS_LIST
import com.github.zzwtsy.tools.Const.STRATEGY_IMAGE_PATH
import com.github.zzwtsy.tools.Database
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.jvm.reloadPluginConfig
import net.mamoe.mirai.utils.info
import java.io.File

object GenshinMiraiBot : KotlinPlugin(
    JvmPluginDescription(
        id = "cn.zzwtsy.genshin",
        name = "Genshin Mirai Bot",
        version = "0.1.0",
    ) {
        author("zzwtsy")
    }
) {
    override fun onEnable() {
        reloadPluginConfig(PluginConfig)
        //初始化文件夹
        INIT_FILE_PATHS_LIST.forEach {
            val file = File(it)
            if (!file.exists()) {
                file.mkdirs()
                logger.debug("已创建{${it}}文件夹")
            }
        }
        //初始化数据库
        Database.init()
        //下载攻略图
        val file = File(STRATEGY_IMAGE_PATH)
        if (!file.exists() || file.listFiles().isNullOrEmpty()) {
            val status = StrategyService().downloadStrategyImage()
            if (status.isNotEmpty())
                logger.info { "没有攻略图的角色${status}" }
        }
        CommandManager.registerCommand(Group)
        logger.info { "Plugin loaded" }
    }
}