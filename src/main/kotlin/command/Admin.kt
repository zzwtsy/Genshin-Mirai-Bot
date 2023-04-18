package com.github.zzwtsy.command

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.data.pluginConfig.PluginConfig
import com.github.zzwtsy.service.miyoushe.StrategyService
import com.github.zzwtsy.tools.Tools.formatMessage
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi

object Admin : BaseCommand, CompositeCommand(
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
        sendMessage("正在更新攻略图。。。")
        val updateStrategyImage = StrategyService().updateStrategyImage()
        if (updateStrategyImage.isEmpty()) {
            sendMessage("已更新攻略图")
        } else {
            sendMessage("${updateStrategyImage.formatMessage()}没有攻略图")
        }
    }
}