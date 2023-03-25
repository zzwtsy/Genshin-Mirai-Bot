package com.github.zzwtsy.utils

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * SQLite 工具类。
 */
object SqliteUtils {

    /**
     * 获取连接。
     */
    fun String.getConnection(): Connection {
        return DriverManager.getConnection(this)
    }

    /**
     * 使用连接并确保资源自动关闭。
     */
    inline fun <T> String.useConnection(block: Connection.() -> T): T {
        return getConnection().use(block)
    }

    /**
     * 执行更新语句。
     */
    inline fun String.executeUpdate(sql: String, block: PreparedStatement.() -> Unit = {}): Int {
        return useConnection { prepareStatement(sql).apply(block).executeUpdate() }
    }

    /**
     * 执行查询语句。
     */
    inline fun <T> String.executeQuery(sql: String, block: ResultSet.() -> T): T {
        return useConnection { createStatement().executeQuery(sql).use { it.block() } }
    }

    /**
     * 创建表。
     */
    inline fun String.createTable(table: String, block: TableBuilder.() -> Unit): Boolean {
        val builder = TableBuilder(table)
        builder.block()
        val columns = builder.columns.joinToString(", ")
        val foreignKeys = builder.foreignKeys.joinToString(", ")
        val sql = "CREATE TABLE IF NOT EXISTS $table ($columns${
            if (foreignKeys.isNotEmpty())
                ", $foreignKeys"
            else
                ""
        })"
        return executeUpdate(sql) > 0
    }

    /**
     * 表构建器。
     */
    class TableBuilder(val table: String) {
        val columns = mutableListOf<String>()
        val foreignKeys = mutableListOf<String>()

        /**
         * 添加列。
         */
        fun column(name: String, type: String, block: ColumnBuilder.() -> Unit = {}) {
            val builder = ColumnBuilder(name, type)
            builder.block()
            columns.add(builder.build())
            if (builder.foreignKey != null) {
                foreignKeys.add(builder.foreignKey!!)
            }
        }

        /**
         * 添加外键。
         */
        fun foreignKey(column: String, foreignTable: String, foreignColumn: String) {
            foreignKeys.add("FOREIGN KEY ($column) REFERENCES $foreignTable($foreignColumn)")
        }
    }

    /**
     * 列构建器。
     */
    class ColumnBuilder(private val name: String, private val type: String) {
        private var primaryKey = false
        private var autoIncrement = false
        private var notNull = false
        var foreignKey: String? = null

        /**
         * 设置为主键。
         */
        fun primaryKey() {
            primaryKey = true
        }

        /**
         * 设置为自增长。
         */
        fun autoIncrement() {
            autoIncrement = true
        }

        /**
         * 设置为非空。
         */
        fun notNull() {
            notNull = true
        }

        /**
         * 添加外键。
         */
        fun foreignKey(column: String, foreignTable: String, foreignColumn: String) {
            foreignKey = "FOREIGN KEY ($column) REFERENCES $foreignTable($foreignColumn)"
        }

        /**
         * 构建列定义。
         */
        fun build(): String {
            val sb = StringBuilder(name).append(" ").append(type)
            if (primaryKey) sb.append(" PRIMARY KEY")
            if (autoIncrement) sb.append(" AUTOINCREMENT")
            if (notNull) sb.append(" NOT NULL")
            return sb.toString()
        }
    }
}
