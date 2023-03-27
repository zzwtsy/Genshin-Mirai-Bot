package com.github.zzwtsy.tools

import com.github.zzwtsy.tools.Const.SQL_CONNECT_URL
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

object DataSource {
    private val config = HikariConfig().apply {
        jdbcUrl = SQL_CONNECT_URL
        driverClassName = "org.sqlite.JDBC"
        maximumPoolSize = 10
        idleTimeout = 60000
        connectionTimeout = 30000
    }
    val dataSource = HikariDataSource(config)
}