package com.github.zzwtsy.dao

import com.github.zzwtsy.dbmodels.Alias
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object AliasDao : Table<Alias>("aliases") {
    var id = int("id").primaryKey().bindTo { it.id }
    var characterId = int("character_id").bindTo { it.characterId }
    var aka = varchar("aka").bindTo { it.aka }
    var elementId = int("element_id").bindTo { it.elementId }
}
