package com.github.zzwtsy.dao

import com.github.zzwtsy.models.Alias
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object Aliases : Table<Alias>("aliases") {
    var characterId = varchar("character_id").primaryKey().bindTo { it.characterId }
    var name = varchar("name").primaryKey().bindTo { it.name }
}
