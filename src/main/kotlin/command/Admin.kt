package com.github.zzwtsy.command

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.data.pluginConfig.PluginConfig
import com.github.zzwtsy.service.miyoushe.StrategyService
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi

object Admin : CompositeCommand(
    GenshinMiraiBot,
    primaryName = "gmbA",
    secondaryNames = PluginConfig.adminSecondaryNames,
    description = "管理员命令权限"
) {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true

    @SubCommand("update", "更新")
    @Description("更新攻略图")
    suspend fun CommandSender.updateStrategy() {

        val updateStrategyImage = StrategyService().updateStrategyImage()
        sendMessage("${updateStrategyImage}已更新攻略图")
    }
}