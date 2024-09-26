package com.vnazarov.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vnazarov.domain.model.Card
import com.vnazarov.domain.usecase.local.CardsFavouriteUseCases
import com.vnazarov.domain.usecase.remote.GetCardsBySetUseCase
import com.vnazarov.domain.usecase.remote.GetSetsUseCase
import com.vnazarov.presentation.util.sealed.SortAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getCardsBySetUseCase: GetCardsBySetUseCase,
    private val getSetsUseCase: GetSetsUseCase,
    private val cardsFavouriteUseCases: CardsFavouriteUseCases
): ViewModel() {

    private val _allCards = MutableLiveData<MutableList<Card>>(mutableListOf())
    val allCards: LiveData<MutableList<Card>> get() = _allCards

    private val _favouriteCards = MutableLiveData<MutableList<Card>>(mutableListOf())
    val favouriteCards: LiveData<MutableList<Card>> get() = _favouriteCards

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        loadCards()
        loadFavourites()
    }

    private fun loadCards() {
        scope.launch {
            val sets = getSetsUseCase.execute()

            sets.forEach { set ->
                try {
                    getCardsBySetUseCase.execute(set).collect { cards ->
                        withContext(Dispatchers.Main) {
                            val uniqueCards = cards.distinctBy { it.name }
                            uniqueCards.forEach { card ->
                                if (_favouriteCards.value?.contains(card) == true) card.favourite = true
                            }

                            val newList = _allCards.value!!
                            newList.addAll(uniqueCards)

                            _allCards.value = newList
                        }
                    }
                } catch (e: Exception) {
                    Log.e("MainViewModel", "loadCards: ${e.message}")
                }
            }
        }
    }

    private fun loadFavourites() {
        scope.launch {
            cardsFavouriteUseCases.getAllFavouritesUseCase.execute().collect { cards ->
                withContext(Dispatchers.Main) {
                    _favouriteCards.value = cards
                }
            }
        }
    }

    fun addToFavourite(card: Card) {
        scope.launch {
            cardsFavouriteUseCases.addToFavouriteUseCase.execute(card)
            loadFavourites()
            if (_allCards.value?.contains(card) == true) card.favourite = true
        }
    }

    fun removeFromFavorites(card: Card) {
        scope.launch {
            cardsFavouriteUseCases.removeFromFavouriteUseCase.execute(card)
            loadFavourites()
            if (_allCards.value?.contains(card) == true) card.favourite = false
        }
    }

    fun sortCards(sortAction: SortAction) {
        when (sortAction) {
            SortAction.SortByClassDown -> sortByClass(false)
            SortAction.SortByClassUp -> sortByClass(true)
            SortAction.SortByCostDown -> sortByCost(false)
            SortAction.SortByCostUp -> sortByCost(true)
        }
    }

    fun searchCard(name: String): Card? =
        if (_allCards.value!!.isNotEmpty())
            _allCards.value!!.find { it.name == name }
        else null

    private fun sortByClass(up: Boolean) {
        if (up) {
            _allCards.value = _allCards.value?.sortedBy { it.classType } as MutableList<Card>?
            _favouriteCards.value = _favouriteCards.value?.sortedBy { it.classType } as MutableList<Card>?
        } else {
            _allCards.value = _allCards.value?.sortedByDescending { it.classType } as MutableList<Card>?
            _favouriteCards.value = _favouriteCards.value?.sortedByDescending { it.classType } as MutableList<Card>?
        }
    }

    private fun sortByCost(up: Boolean) {
        if (up) {
            _allCards.value = _allCards.value?.sortedBy { it.cost } as MutableList<Card>?
            _favouriteCards.value = _favouriteCards.value?.sortedBy { it.cost } as MutableList<Card>?
        } else {
            _allCards.value = _allCards.value?.sortedByDescending { it.cost } as MutableList<Card>?
            _favouriteCards.value = _favouriteCards.value?.sortedByDescending { it.cost } as MutableList<Card>?

        }
    }
}