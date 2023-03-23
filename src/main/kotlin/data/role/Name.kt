package com.github.zzwtsy.data.role

import kotlinx.serialization.SerialName
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

/**
 * 原神角色名字与别名
 * @author zzwtsy
 * @date 2023/03/23
 */
object Name : AutoSavePluginData("name") {
    @SerialName("20000000")
    val traveler by value(
        arrayListOf(
            "主角", "旅行者", "卑鄙的外乡人", "荣誉骑士", "爷", "风主", "岩主", "雷主", "草主", "履刑者", "抽卡不歪真君"
        )
    )

    @SerialName("10000002")
    val kamisatoAyaka by value(
        arrayListOf(
            "神里绫华",
            "Kamisato Ayaka",
            "Ayaka",
            "ayaka",
            "神里",
            "绫华",
            "神里凌华",
            "凌华",
            "白鹭公主",
            "神里大小姐",
            "小乌龟",
            "龟龟"
        )
    )

    @SerialName("10000003")
    val jean by value(
        arrayListOf(
            "琴",
            "Jean",
            "jean",
            "团长",
            "代理团长",
            "琴团长",
            "蒲公英骑士",
            "琴·古恩希尔德",
            "古恩希尔"
        )
    )
    //TODO: 添加原神角色名字与别名
}