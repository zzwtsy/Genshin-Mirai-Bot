package com.github.zzwtsy.dao
import com.github.zzwtsy.models.Character
import org.ktorm.schema.*

object Characters : Table<Character>("characters"){
	var id = varchar("id").primaryKey().bindTo { it.id }
	var name = text("name").bindTo { it.name }
	var strategyMd5 = text("strategy_md5").bindTo { it.strategyMd5 }
}
