package com.vnazarov.domain.repository.local

import com.vnazarov.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface LocalCardsRepository {

    suspend fun getAllFavouriteCards(): Flow<MutableList<Card>>

    suspend fun addCardToFavourite(card: Card)

    suspend fun removeCardFromFavourites(card: Card)
}