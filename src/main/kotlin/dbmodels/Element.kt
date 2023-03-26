package com.github.zzwtsy.dbmodels

import org.ktorm.entity.Entity

interface Element : Entity<Element> {
    companion object : Entity.Factory<Element>()

    var id: Int
    var name: String

    var idAlias: Alias?
    var idCharacterStrategy: CharacterStrategy?
    var idStrategy: Strategy?
}
