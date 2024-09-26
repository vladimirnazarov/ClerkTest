package com.vnazarov.data.remote

import com.vnazarov.data.dto.CardDto
import com.vnazarov.data.dto.InfoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardsApiService {

    @GET("/cards/sets/%7B{set}%7D")
    suspend fun getCardsBySet(@Path("set") set: String): List<CardDto>?

    @GET("/info")
    suspend fun getInfo(): InfoResponseDto
}