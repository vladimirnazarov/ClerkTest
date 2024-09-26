package com.vnazarov.data.local.mapper

import com.vnazarov.data.local.entity.CardEntity
import com.vnazarov.domain.model.Card

fun CardEntity.toCard() = Card(
    id = this.id,
    name = this.name,
    classType = this.classType,
    description = this.description,
    cost = this.cost,
    attack = this.attack,
    health = this.health,
    image = this.image
)

fun Card.toCardEntity() = CardEntity(
    id = this.id,
    name = this.name,
    classType = this.classType,
    description = this.description,
    cost = this.cost,
    attack = this.attack,
    health = this.health,
    image = this.image
)