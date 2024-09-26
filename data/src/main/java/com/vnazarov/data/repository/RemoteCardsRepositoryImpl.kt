package com.vnazarov.data.repository

import com.vnazarov.data.remote.CardsApiService
import com.vnazarov.data.dto.toDomain
import com.vnazarov.domain.model.Card
import com.vnazarov.domain.repository.remote.RemoteCardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteCardsRepositoryImpl(
    private val apiService: CardsApiService,
): RemoteCardsRepository {

    override suspend fun getCardsBySet(set: String): Flow<MutableList<Card>> = flow {
        val cards = apiService.getCardsBySet(set)
        cards?.map { it.toDomain() }?.let { emit(it as MutableList<Card>) }
    }

    override suspend fun getSets() = apiService.getInfo().sets
}