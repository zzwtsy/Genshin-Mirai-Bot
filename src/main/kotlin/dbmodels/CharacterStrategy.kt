package com.github.zzwtsy.dbmodels

import org.ktorm.entity.Entity

interface CharacterStrategy : Entity<CharacterStrategy> {
    companion object : Entity.Factory<CharacterStrategy>()

    var id: Int
    var characterId: Int
    var strategyId: Int
    var elementId: Int


    var characterIdCharacter: Character?
    var strategyIdStrategy: Strategy?
    var elementIdElement: Element?
}
