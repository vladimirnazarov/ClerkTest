package com.vnazarov.domain.model

data class Card(
    val id: Int,
    val name: String?,
    val classType: String?,
    val description: String?,
    val cost: Int?,
    val attack: Int?,
    val health: Int?,
    val image: String?,
    var favourite: Boolean = false
)