package com.github.zzwtsy.dao

import com.github.zzwtsy.dbmodels.Element
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object ElementDao : Table<Element>("elements") {
    var id = int("id").primaryKey().bindTo { it.id }
    var name = varchar("name").bindTo { it.name }
}
