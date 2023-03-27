package com.github.zzwtsy.models

import org.ktorm.entity.Entity

interface Character : Entity<Character> {
    companion object : Entity.Factory<Character>()

    var id: String
    var name: String
    var strategyMd5: String

    var idAlias: Alias?
}
