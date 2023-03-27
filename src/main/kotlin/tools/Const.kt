package com.github.zzwtsy.tools

import com.github.zzwtsy.GenshinMiraiBot

object Const {// api
    /** 角色别名数据文件 url */
    const val ROLE_NAME_ALIASES_FILE_URL = "https://static.yumdeb.top/img/GenshinImpact/bot/roleNameAliases.yml"

    /** 原神角色名称,网址 */
    const val ROLE_NAMES_URL =
        "https://api-static.mihoyo.com/common/blackboard/ys_obc/v1/home/content/list?app_sn=ys_obc&channel_id=189"

    /** 获取米游社帖子合集 api */
    const val MYS_POSTS_URL =
        "https://bbs-api.mihoyo.com/post/wapi/getPostFullInCollection?&gids=2&order_type=2&collection_id="

    /** 将攻略图片转换为 jpeg 格式减小图片大小 */
    const val oss = "?x-oss-process=image/format,jpg"

    /** 西风驿站攻略帖合集 id */
    val STRATEGY_SOURCE = arrayOf(
        839176, /*蒙德角色图鉴合集*/
        839179, /*璃月角色图鉴合集*/
        839181, /*稻妻角色图鉴合集*/
        1180811 /*须弥角色图鉴合集*/
    )

    // paths
    private val pluginDataPath = "data/${GenshinMiraiBot.dataFolder.name}"

    /** 攻略图像存放路径 */
    val STRATEGY_IMAGE_PATH = "$pluginDataPath/image/strategy/"

    /** 数据库文件路径 */
    val DB_FILE_PATH = "${pluginDataPath}/data.db"

    /** 数据库连接 url */
    val SQL_CONNECT_URL = "jdbc:sqlite:$DB_FILE_PATH"

    /** 临时文件夹路径 */
    val TEMP_PATH = "$pluginDataPath/temp"

    /** 需要初始化的文件夹路径列表 */
    val INIT_FILE_PATHS_LIST = arrayOf(TEMP_PATH, STRATEGY_IMAGE_PATH)

    /** 角色别名数据文件存放路径 */
    val ROLE_NAME_ALIASES_PATH = "$TEMP_PATH/roleName.yml"
}