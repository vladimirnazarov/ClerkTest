package com.vnazarov.data.repository

import com.vnazarov.data.local.dao.CardsDao
import com.vnazarov.data.local.mapper.toCard
import com.vnazarov.data.local.mapper.toCardEntity
import com.vnazarov.domain.model.Card
import com.vnazarov.domain.repository.local.LocalCardsRepository
import kotlinx.coroutines.flow.map

class LocalCardsRepositoryImpl(
    private val cardsDao: CardsDao
): LocalCardsRepository {

    override suspend fun getAllFavouriteCards() = cardsDao.getAllCards().map { entities ->
        entities.map { it.toCard() } as MutableList<Card>
    }

    override suspend fun addCardToFavourite(card: Card) {
        cardsDao.insertCard(card.toCardEntity())
    }

    override suspend fun removeCardFromFavourites(card: Card) {
        cardsDao.deleteCard(card.toCardEntity())
    }
}