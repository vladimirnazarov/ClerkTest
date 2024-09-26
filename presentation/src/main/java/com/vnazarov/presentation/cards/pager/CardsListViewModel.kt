package com.vnazarov.presentation.cards.pager

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vnazarov.domain.model.Card
import com.vnazarov.presentation.MainViewModel
import com.vnazarov.presentation.cards.adapter.CardsAdapter

class CardsListViewModel(
    private val activityViewModel: MainViewModel,
): ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory(private val activityViewModel: MainViewModel): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CardsListViewModel(activityViewModel) as T
        }
    }

    fun getListByType(listType: String) =
        if (listType == LIST_TYPE_FAVOURITE) activityViewModel.favouriteCards.value!!
        else activityViewModel.allCards.value!!

    fun observeAllCards(
        listType: String,
        viewLifecycleOwner: LifecycleOwner,
        adapter: CardsAdapter,
        viewToHide: View
    ) {
        if (listType == LIST_TYPE_FAVOURITE) observeValue(activityViewModel.favouriteCards, viewLifecycleOwner, adapter, viewToHide)
        else observeValue(activityViewModel.allCards, viewLifecycleOwner, adapter, viewToHide)
    }

    private fun observeValue(
        value: LiveData<MutableList<Card>>,
        viewLifecycleOwner: LifecycleOwner,
        adapter: CardsAdapter,
        viewToHide: View
    ) {
        value.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                adapter.updateCards(list)
                viewToHide.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val LIST_TYPE_FAVOURITE = "list_type_favourite"
    }
}