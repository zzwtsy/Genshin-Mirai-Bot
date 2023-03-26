package com.github.zzwtsy.dbmodels

import org.ktorm.entity.Entity

interface Strategy : Entity<Strategy> {
    companion object : Entity.Factory<Strategy>()

    var id: Int
    var md5: String
    var elementId: Int

    var elementIdElement: Element?
    var idCharacterStrategy: CharacterStrategy?
}
