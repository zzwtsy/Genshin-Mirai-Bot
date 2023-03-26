package com.github.zzwtsy.dbmodels

import org.ktorm.entity.Entity

interface Alias : Entity<Alias> {
    companion object : Entity.Factory<Alias>()

    var id: Int
    var characterId: Int
    var aka: String
    var elementId: Int

    var characterIdCharacter: Character?
    var elementIdElement: Element?
}
