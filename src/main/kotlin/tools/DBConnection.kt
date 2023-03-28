package com.github.zzwtsy.tools

import com.github.zzwtsy.tools.Const.SQL_CONNECT_URL
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

/**
 * 获取数据库连接池
 */
object DBConnection {
    private val config = HikariConfig().apply {
        // 数据库连接 URL
        jdbcUrl = SQL_CONNECT_URL
        // JDBC 驱动类名
        driverClassName = "org.sqlite.JDBC"
        // 最小空闲连接数为 1
        minimumIdle = 5
        // 最大连接数为 50
        maximumPoolSize = 50
        // 连接可以空闲的最长时间为 60 秒
        idleTimeout = 60000
        // 连接超时时间为 30 秒
        connectionTimeout = 30000
        // 连接池名称
        poolName = "Genshin Mirai Bot - HikariCP"
    }

    /**
     * HikariCP 数据源对象，用于获取连接
     */
    val dataSource = HikariDataSource(config)
}