package com.github.zzwtsy.dao

import com.github.zzwtsy.models.Alias
import org.ktorm.schema.Table
import org.ktorm.schema.text

object Aliases : Table<Alias>("Aliases") {
    val characterId = text("character_id").references(Characters) { it.characterIdCharacter }.bindTo { it.characterId }
    val name = text("name").primaryKey().bindTo { it.name }
}
