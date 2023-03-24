package com.github.zzwtsy.tools

import com.github.zzwtsy.GenshinMiraiBot

/** 攻略图像存放路径 */
val strategyImagePath = "data/${GenshinMiraiBot.dataFolder.name}/image/strategy"

/** 原神角色名称,网址 */
const val roleNamesUrl =
    "https://api-static.mihoyo.com/common/blackboard/ys_obc/v1/home/content/list?app_sn=ys_obc&channel_id=189"

/** 获取米游社帖子合集 api */
const val mysPostsUrl =
    "https://bbs-api.mihoyo.com/post/wapi/getPostFullInCollection?&gids=2&order_type=2&collection_id="

/** 西风驿站攻略帖合集 id */
val strategySource = arrayOf(
    839176, /*蒙德角色图鉴合集*/
    839179, /*璃月角色图鉴合集*/
    839181, /*稻妻角色图鉴合集*/
    1180811 /*须弥角色图鉴合集*/
)