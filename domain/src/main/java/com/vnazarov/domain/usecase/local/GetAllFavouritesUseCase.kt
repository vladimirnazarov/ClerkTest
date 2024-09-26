package com.vnazarov.domain.usecase.local

import com.vnazarov.domain.repository.local.LocalCardsRepository

class GetAllFavouritesUseCase(
    private val repository: LocalCardsRepository
) {

    suspend fun execute() = repository.getAllFavouriteCards()
}