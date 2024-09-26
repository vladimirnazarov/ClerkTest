package com.vnazarov.domain.usecase.remote

import com.vnazarov.domain.repository.remote.RemoteCardsRepository

class GetSetsUseCase(
    private val repository: RemoteCardsRepository
) {

    suspend fun execute() = repository.getSets()
}