package com.vnazarov.domain.usecase.remote

import com.vnazarov.domain.repository.remote.RemoteCardsRepository

class GetCardsBySetUseCase(
    private val repository: RemoteCardsRepository
) {

    suspend fun execute(set: String) = repository.getCardsBySet(set)
}