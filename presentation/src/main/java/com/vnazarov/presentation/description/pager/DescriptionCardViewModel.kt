package com.vnazarov.presentation.description.pager

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vnazarov.domain.model.Card
import com.vnazarov.presentation.MainViewModel

class DescriptionCardViewModel(
    private val activityViewModel: MainViewModel
): ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory(private val activityViewModel: MainViewModel): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DescriptionCardViewModel(activityViewModel) as T
        }
    }

    private val _card = MutableLiveData<Card>()

    private val _descriptionCardTitle = MutableLiveData("")
    val descriptionCardTitle: LiveData<String> get() = _descriptionCardTitle

    private val _descriptionCardBody = MutableLiveData("")
    val descriptionCardBody: LiveData<String> get() = _descriptionCardBody

    private val _isFavourite = MutableLiveData(0)
    val isFavourite: LiveData<Int> get() = _isFavourite

    private val _buttonText = MutableLiveData("")
    val buttonText: LiveData<String> get() = _buttonText

    private val _buttonAction = MutableLiveData<() -> Unit>(::emptyAction)
    val buttonAction: LiveData<() -> Unit> get() = _buttonAction

    private fun emptyAction() = run { }

    fun insertCard(card: Card) {
        _card.value = card
        setValuesByCard()
    }

    private fun setValuesByCard() {
        _descriptionCardTitle.value = _card.value?.name
        _descriptionCardBody.value = _card.value?.description
        _isFavourite.value =
            if (_card.value!!.favourite) View.VISIBLE
            else View.GONE

        setButton()
    }

    private fun setButton() {
        _buttonAction.value =
            if (_isFavourite.value == View.VISIBLE) ::removeFromFavourites
            else ::addToFavourites

        _buttonText.value =
            if (_isFavourite.value == View.VISIBLE) "Remove from favourites"
            else "Add to favourites"
    }

    private fun addToFavourites() {
        activityViewModel.addToFavourite(_card.value!!)

        _card.value!!.favourite = !_card.value!!.favourite
        insertCard(_card.value!!)
    }

    private fun removeFromFavourites() {
        activityViewModel.removeFromFavorites(_card.value!!)

        _card.value!!.favourite = !_card.value!!.favourite
        insertCard(_card.value!!)
    }

    fun getListByType(listType: String) =
        if (listType == LIST_TYPE_FAVOURITE) activityViewModel.favouriteCards.value!!
        else activityViewModel.allCards.value!!

    companion object {
        private const val LIST_TYPE_FAVOURITE = "list_type_favourite"
    }
}