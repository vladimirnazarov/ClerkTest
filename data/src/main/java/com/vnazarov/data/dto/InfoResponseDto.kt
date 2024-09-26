package com.vnazarov.data.dto

import com.google.gson.annotations.SerializedName

data class InfoResponseDto(

    @SerializedName("sets")
    val sets: List<String>
)