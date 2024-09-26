package com.vnazarov.domain.usecase.local

import com.vnazarov.domain.model.Card
import com.vnazarov.domain.repository.local.LocalCardsRepository

class RemoveFromFavouriteUseCase(
    private val repository: LocalCardsRepository
) {

    suspend fun execute(card: Card) = repository.removeCardFromFavourites(card)
}