package com.vnazarov.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_cards")
data class CardEntity(

    @PrimaryKey
    val id: Int,

    val name: String?,

    val classType: String?,

    val description: String?,

    val cost: Int?,

    val attack: Int?,

    val health: Int?,

    val image: String?
)
