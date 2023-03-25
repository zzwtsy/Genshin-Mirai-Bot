package com.github.zzwtsy.command

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.dao.RoleDao
import com.github.zzwtsy.data.pluginConfig.PluginConfig
import com.github.zzwtsy.tools.strategyImagePath
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import java.io.File

object Group : CompositeCommand(
    GenshinMiraiBot,
    primaryName = "gmb",
    secondaryNames = PluginConfig.secondaryNames,
    description = "获取攻略图命令"
) {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true

    @SubCommand("攻略", "strategy")
    @Description("获取角色攻略图")
    suspend fun CommandContext.strategy(roleName: String = "") {
        if (roleName.isEmpty()) {
            sender.subject?.sendMessage(originalMessage.quote().plus("请指定角色名"))
            return
        }
        val roleId = RoleDao.getRoleIdByName(roleName)
        if (roleId == null) {
            sender.subject?.sendMessage(
                originalMessage.quote().plus("没有${roleName}的攻略")
            )
        } else {
            sender.subject?.sendImage(File("$strategyImagePath/$roleId.png"))
        }
    }
}