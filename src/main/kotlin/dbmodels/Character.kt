package com.github.zzwtsy.dbmodels

import org.ktorm.entity.Entity

interface Character : Entity<Character> {
    companion object : Entity.Factory<Character>()

    var id: Int
    var name: String

    var idAlias: Alias?
    var idCharacterStrategy: CharacterStrategy?
}
