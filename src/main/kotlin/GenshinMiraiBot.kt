package com.github.zzwtsy

import com.github.zzwtsy.miyoushe.Strategy
import com.github.zzwtsy.tools.strategyImagePath
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info
import java.io.File

object GenshinMiraiBot : KotlinPlugin(
    JvmPluginDescription(
        id = "com.github.zzwtsy.genshin-bot",
        name = "Genshin Mirai Bot",
        version = "0.1.0",
    ) {
        author("zzwtsy")
    }
) {
    override fun onEnable() {
        //下载攻略图
        val file = File(strategyImagePath)
        println(file.listFiles()?.size)
        if (!file.exists() || file.listFiles() == null) {
            val status = Strategy().downloadStrategyImage()
            if (status.isNotEmpty())
                logger.info { "没有攻略图的角色${status}" }
        }
//        CommandManager.registerCommand(Group)
        logger.info { "Plugin loaded" }
    }
}