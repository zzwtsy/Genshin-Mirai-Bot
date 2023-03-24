package com.github.zzwtsy.data.pluginConfig.role

import com.github.zzwtsy.data.pluginConfig.role.RoleName.RoleName
import kotlinx.serialization.Serializable

/**
 * 原神角色名
 *
 * {
 *   "data": {
 *     "list": [
 *       {
 *         "id": 189,
 *         "name": "图鉴",
 *         "children": [
 *           {
 *             "id": 25,
 *             "name": "角色",
 *             "list": [
 *               {
 *                 "title": "米卡"
 *               },
 *               {
 *                 "title": "1"
 *               }
 *             ]
 *           }
 *         ]
 *       }
 *     ]
 *   }
 * }
 *
 * @author zzwtsy
 * @date 2023/03/24
 * @constructor 创建[RoleName]
 */
class RoleName {
    @Serializable
    data class GenshinData(
        val data: Data
    )

    @Serializable
    data class Data(val list: List<ListItem>)

    @Serializable
    data class ListItem(
        val children: List<ChildrenItem>,
        val name: String = "",
        val id: Int = 0
    )

    @Serializable
    data class ChildrenItem(
        val name: String = "",
        val id: Int = 0,
        val list: List<RoleName>
    )

    @Serializable
    data class RoleName(
        val title: String
    )
}