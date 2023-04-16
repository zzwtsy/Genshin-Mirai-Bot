package com.github.zzwtsy.command

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.data.pluginConfig.PluginConfig
import com.github.zzwtsy.data.pluginConfig.PluginRegexConfig
import com.github.zzwtsy.service.AliasService
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
    secondaryNames = PluginConfig.groupSecondaryNames,
    description = "群聊命令权限"
) {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true

    /** 旅行者别名正则表达式 */
    private val travelersAlias = PluginRegexConfig.travelersAliasRegex.toRegex()

    /** 角色名位于元素类型前 */
    private val roleElementType = PluginRegexConfig.roleElementTypeRegex.toRegex()

    /** 元素类型位于角色名前 */
    private val elementTypeRole = PluginRegexConfig.elementTypeRoleRegex.toRegex()

    @SubCommand("攻略", "strategy", "gl")
    @Description("获取角色攻略图")
    suspend fun CommandSender.strategy(roleName: String = "") {
        // 检查角色名是否为空
        if (roleName.isEmpty()) {
            quoteReply("请指定角色名")
            return
        }

        // 提示
        quoteReply("正在获取攻略图")

        val imageMd5 = when {
            // 如果 roleName 包含在 travelers 中
            travelersAlias.containsMatchIn(roleName) -> {
                // 检查是否包含元素类型
                if (!roleElementType.containsMatchIn(roleName)
                    &&
                    !elementTypeRole.containsMatchIn(roleName)
                ) {
                    // 如果没有则提示用户需要添加元素类型
                    quoteReply("请添加元素类型")
                    return
                }
                // 如果包含了正确的元素类型，则解析角色名并获取攻略图片的 MD5 值
                val parser = travelersParser(roleName)
                parser?.let { AliasService.getStrategyMd5ByAlias(it) }
            }
            // 如果 roleName 不在 travelers 中，则直接根据角色名获取攻略图片的 MD5 值
            else -> AliasService.getStrategyMd5ByAlias(roleName)
        }

        // 如果获取到的 MD5 值为 null，则表示没有找到对应的攻略图片
        // 否则发送攻略图片给用户
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
        if (subject != null) sendMessage(
            (this as CommandSenderOnMessage<*>).fromEvent.source.quote().plus(replyMessage)
        )
        else sendMessage(replyMessage)
    }

    /**
     * 旅行者特殊处理
     * @param [message] 消息
     * @return [String]
     */
    private fun travelersParser(message: String): String? {
        return when ("[草雷水火岩风冰]".toRegex().find(message)?.value) {
            "风" -> "风主"
            "岩" -> "岩主"
            "雷" -> "雷主"
            "草" -> "草主"
            "水" -> "水主"
            "火" -> "火主"
            "冰" -> "冰主"
            else -> null
        }
    }
}