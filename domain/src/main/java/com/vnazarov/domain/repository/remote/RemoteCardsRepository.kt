package com.vnazarov.domain.repository.remote

import com.vnazarov.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface RemoteCardsRepository {

    suspend fun getCardsBySet(set: String): Flow<MutableList<Card>>

    suspend fun getSets(): List<String>
}