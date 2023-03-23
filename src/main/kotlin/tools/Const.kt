package com.github.zzwtsy.tools

import com.github.zzwtsy.GenshinMiraiBot

/** 攻略图像存放路径 */
val strategyImagePath = "data/${GenshinMiraiBot.dataFolder.name}/image/strategy"

/** 原神角色名称,网址 */
const val roleNamesUrl =
    "https://api-static.mihoyo.com/common/blackboard/ys_obc/v1/home/content/list?app_sn=ys_obc&channel_id=189"

/** 获取米游社帖子 api */
const val mysPostsUrl =
    "https://bbs-api.mihoyo.com/post/wapi/getPostFullInCollection?&gids=2&order_type=2&collection_id="

/** 西风驿站攻略帖 id */
val strategySource = arrayOf(839176, 839179, 839181, 1180811)