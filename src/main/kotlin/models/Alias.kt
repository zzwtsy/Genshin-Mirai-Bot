package com.github.zzwtsy.models

import org.ktorm.entity.Entity

interface Alias : Entity<Alias> {
    companion object : Entity.Factory<Alias>()

    var characterId: String
    var name: String

    var characterIdCharacter: Character?
}
