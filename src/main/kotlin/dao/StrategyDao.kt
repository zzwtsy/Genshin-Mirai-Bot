package com.github.zzwtsy.dao

import com.github.zzwtsy.dbmodels.Strategy
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object StrategyDao : Table<Strategy>("strategies") {
    var id = int("id").primaryKey().bindTo { it.id }
    var md5 = varchar("md5").bindTo { it.md5 }
    var elementId = int("element_id").bindTo { it.elementId }
}
