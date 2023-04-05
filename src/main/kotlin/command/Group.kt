package com.github.zzwtsy.command

import com.github.zzwtsy.GenshinMiraiBot
import com.github.zzwtsy.data.pluginConfig.PluginConfig
import com.github.zzwtsy.service.CharacterService
import com.github.zzwtsy.tools.Const.STRATEGY_IMAGE_PATH
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import java.io.File

object Group : BaseCommand, CompositeCommand(
    GenshinMiraiBot,
    primaryName = "gmb",
    secondaryNames = PluginConfig.groupSecondaryNames,
    description = "群聊命令权限"
) {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true

    @SubCommand("攻略", "strategy", "gl")
    @Description("获取角色攻略图")
    suspend fun CommandSender.strategy(roleName: String = "") {


        val noSpaceRoleName = space.replace(roleName, "")

        // 检查角色名是否为空
        if (noSpaceRoleName.isEmpty()) {
            quoteReply("请指定角色名")
            return
        }

        // 提示
        quoteReply("正在获取『$noSpaceRoleName』攻略图")

        val imageMd5 = when {
            // 如果 roleName 包含在 travelers 中
            travelersAlias.containsMatchIn(noSpaceRoleName) -> {
                // 检查是否包含元素类型
                if (!roleElementType.containsMatchIn(noSpaceRoleName)
                    &&
                    !elementTypeRole.containsMatchIn(noSpaceRoleName)
                ) {
                    // 如果没有则提示用户需要添加元素类型
                    quoteReply("请添加元素类型")
                    return
                }
                // 如果包含了正确的元素类型，则解析角色名并获取攻略图片的 MD5 值
                val parser = travelersParser(noSpaceRoleName) ?: kotlin.run {
                    quoteReply("元素类型错误")
                    return
                }
                CharacterService.getStrategyMd5ByAlias(parser)
            }
            // 如果 roleName 不在 travelers 中，则直接根据角色名获取攻略图片的 MD5 值
            else -> CharacterService.getStrategyMd5ByAlias(noSpaceRoleName)
        }

        // 如果获取到的 MD5 值为 null，则表示没有找到对应的攻略图片
        // 否则发送攻略图片给用户
        if (imageMd5 == null) {
            quoteReply("没有「${noSpaceRoleName}」的攻略")
        } else {
            subject?.sendImage(File("$STRATEGY_IMAGE_PATH/$imageMd5.jpeg"))
        }
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