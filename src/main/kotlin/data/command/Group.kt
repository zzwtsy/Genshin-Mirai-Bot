package com.github.zzwtsy.data.command

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.data.PluginConfig
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.message.data.MessageSource.Key.quote

object Group : CompositeCommand(
    GenshinMiraiBot,
    primaryName = "gmb",
    secondaryNames = PluginConfig.secondaryNames,
) {
    @SubCommand("攻略", "strategy")
    @Description("获取角色攻略图")
    suspend fun strategy(commandContext: CommandContext, roleName: String = "") {
        if (roleName.isEmpty()) {
            val originalMessage = commandContext.originalMessage
            commandContext.sender.subject?.sendMessage(originalMessage.quote().plus("请指定角色名"))
            return
        }

    }
}