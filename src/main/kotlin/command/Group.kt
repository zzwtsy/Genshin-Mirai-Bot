package com.github.zzwtsy.command

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.data.pluginConfig.PluginConfig
import com.github.zzwtsy.service.CharacterService
import com.github.zzwtsy.tools.Const.STRATEGY_IMAGE_PATH
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CommandSenderOnMessage
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
    description = "群聊命令权限"
) {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true

    @SubCommand("攻略", "strategy", "gl")
    @Description("获取角色攻略图")
    suspend fun CommandSender.strategy(roleName: String = "") {
        if (roleName.isEmpty()) {
            quoteReply("请指定角色名")
            return
        }
        val imageMd5 = CharacterService.getStrategyMd5ByAlias(roleName)
        if (imageMd5 == null) {
            quoteReply("没有「${roleName}」的攻略")
        } else {
            subject?.sendImage(File("$STRATEGY_IMAGE_PATH/$imageMd5.jpeg"))
        }
    }

    /**
     * 引用回复
     * @param [replyMessage] 回复的消息
     */
    private suspend fun CommandSender.quoteReply(replyMessage: String) {
        if (subject != null)
            sendMessage((this as CommandSenderOnMessage<*>).fromEvent.source.quote().plus(replyMessage))
        else
            sendMessage(replyMessage)
    }
}