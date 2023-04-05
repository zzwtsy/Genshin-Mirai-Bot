package com.github.zzwtsy.command

import com.github.zzwtsy.data.pluginConfig.PluginRegexConfig
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.message.data.MessageSource.Key.quote

interface BaseCommand {

    /** 旅行者别名正则表达式 */
    val travelersAlias: Regex
        get() = PluginRegexConfig.travelersAliasRegex.toRegex()

    /** 角色名位于元素类型前 */
    val roleElementType: Regex
        get() = PluginRegexConfig.roleElementTypeRegex.toRegex()

    /** 元素类型位于角色名前 */
    val elementTypeRole: Regex
        get() = PluginRegexConfig.elementTypeRoleRegex.toRegex()

    /** 空格正则 */
    val space: Regex
        get() = " ".toRegex()

    /**
     * 引用回复
     * @param [replyMessage] 回复的消息
     */
    suspend fun CommandSender.quoteReply(replyMessage: String) {
        if (subject != null) sendMessage(
            (this as CommandSenderOnMessage<*>).fromEvent.source.quote().plus(replyMessage)
        )
        else sendMessage(replyMessage)
    }
}