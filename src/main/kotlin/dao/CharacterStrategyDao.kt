package com.github.zzwtsy.dao

import com.github.zzwtsy.dbmodels.CharacterStrategy
import org.ktorm.schema.Table
import org.ktorm.schema.int

object CharacterStrategyDao : Table<CharacterStrategy>("character_strategy") {
    var id = int("id").primaryKey().bindTo { it.id }
    var characterId = int("character_id").bindTo { it.characterId }
    var strategyId = int("strategy_id").bindTo { it.strategyId }
    var elementId = int("element_id").bindTo { it.elementId }
}
