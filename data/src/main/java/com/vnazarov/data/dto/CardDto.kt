package com.vnazarov.data.dto

import android.text.Html
import com.google.gson.annotations.SerializedName
import com.vnazarov.domain.model.Card

data class CardDto(

    @SerializedName("dbfId")
    val id: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("type")
    val classType: String?,

    @SerializedName("text")
    val description: String?,

    @SerializedName("cost")
    val cost: Int? = null,

    @SerializedName("attack")
    val attack: Int? = null,

    @SerializedName("health")
    val health: Int? = null,

    @SerializedName("img")
    val image: String? = null
)

fun CardDto.toDomain(): Card {
    val description = Html.fromHtml(this.description ?: "", Html.FROM_HTML_MODE_LEGACY).toString()
        .replace("\\n", " ")
        .replace("[x]", "")
        .replace("_", " ")
        .replace("$", "")

    return Card(
        id = this.id,
        name = this.name,
        classType = this.classType,
        description = description,
        cost = this.cost,
        attack = this.attack,
        health = this.health,
        image = this.image
    )
}