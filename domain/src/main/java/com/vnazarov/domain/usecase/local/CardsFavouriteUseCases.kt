package com.vnazarov.domain.usecase.local

data class CardsFavouriteUseCases(
    val addToFavouriteUseCase: AddToFavouriteUseCase,
    val getAllFavouritesUseCase: GetAllFavouritesUseCase,
    val removeFromFavouriteUseCase: RemoveFromFavouriteUseCase
)