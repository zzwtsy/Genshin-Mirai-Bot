package com.github.zzwtsy.dao

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.EntitySequence
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.BaseTable
import org.ktorm.schema.ColumnDeclaring

/**
 * codeGen version:2.2
 * codeGen github:https://github.com/yuzd/AntData.ORM
 **/
object Tables {
    val alias = AliasDao
    val Database.aliases get() = this.sequenceOf(AliasDao, false)
    val character = CharacterDao
    val Database.characters get() = this.sequenceOf(CharacterDao, false)
    val characterStrategy = CharacterStrategyDao
    val Database.characterStrategies get() = this.sequenceOf(CharacterStrategyDao, false)
    val element = ElementDao
    val Database.elements get() = this.sequenceOf(ElementDao, false)
    val strategy = StrategyDao
    val Database.strategies get() = this.sequenceOf(StrategyDao, false)
    fun <E : Any, T : BaseTable<E>> EntitySequence<E, T>.insert(block: AssignmentsBuilder.(T) -> Unit): Int {
        return this.database.insert(this.sourceTable, block)
    }

    fun <E : Any, T : BaseTable<E>> EntitySequence<E, T>.update(block: UpdateStatementBuilder.(T) -> Unit): Int {
        return this.database.update(this.sourceTable, block)
    }

    fun <E : Any, T : BaseTable<E>> EntitySequence<E, T>.upgrade(block: UpdateStatementBuilder.(T) -> Unit): Int {
        return this.database.update(this.sourceTable, block)
    }

    fun <E : Any, T : BaseTable<E>> EntitySequence<E, T>.delete(predicate: (T) -> ColumnDeclaring<Boolean>): Int {
        return this.database.delete(this.sourceTable, predicate)
    }

    fun <E : Any, T : BaseTable<E>> EntitySequence<E, T>.joinReferencesAndSelect(): EntitySequence<E, T> {
        return this.database.sequenceOf(this.sourceTable, true)
    }
}
