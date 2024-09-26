package com.vnazarov.clerktest.di

import com.vnazarov.domain.usecase.local.AddToFavouriteUseCase
import com.vnazarov.domain.usecase.local.CardsFavouriteUseCases
import com.vnazarov.domain.usecase.local.GetAllFavouritesUseCase
import com.vnazarov.domain.usecase.local.RemoveFromFavouriteUseCase
import com.vnazarov.domain.usecase.remote.GetCardsBySetUseCase
import com.vnazarov.domain.usecase.remote.GetSetsUseCase
import com.vnazarov.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val domainModule = module {
    single {
        GetCardsBySetUseCase(get())
    }

    single {
        GetSetsUseCase(get())
    }

    //DB
    single { GetAllFavouritesUseCase(get()) }

    single { AddToFavouriteUseCase(get()) }

    single { RemoveFromFavouriteUseCase(get()) }

    single {
        CardsFavouriteUseCases(
            addToFavouriteUseCase = get(),
            getAllFavouritesUseCase = get(),
            removeFromFavouriteUseCase = get()
        )
    }

    viewModel { MainViewModel(get(), get(), get()) }
}