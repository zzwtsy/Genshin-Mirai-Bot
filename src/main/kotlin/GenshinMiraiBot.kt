package com.github.zzwtsy

import com.github.zzwtsy.miyoushe.strategy.Download
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
        val localStrategyImageNumber = File(strategyImagePath).listFiles().size
        if (!File(strategyImagePath).exists() || localStrategyImageNumber == 0)
            Download().downloadStrategyImage()
        logger.info { "Plugin loaded" }
    }
}