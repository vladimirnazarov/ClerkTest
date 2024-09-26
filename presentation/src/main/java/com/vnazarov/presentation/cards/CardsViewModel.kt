package com.vnazarov.presentation.cards

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vnazarov.presentation.MainViewModel
import com.vnazarov.presentation.util.DialogUtil
import com.vnazarov.presentation.util.sealed.DialogType

class CardsViewModel(
    private val activityViewModel: MainViewModel,
): ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory(private val activityViewModel: MainViewModel): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CardsViewModel(activityViewModel) as T
        }
    }

    fun createDialog(context: Context, type: DialogType, searchAction: ((String) -> Unit)? = null) {
        when (type) {
            DialogType.SearchDialog -> createSearchDialog(context, searchAction!!)
            DialogType.SortDialog -> createSortDialog(context)
        }
    }

    fun searchCardByName(name: String) = activityViewModel.searchCard(name)

    private fun createSearchDialog(context: Context, action: (String) -> Unit) {
        DialogUtil.createSearchDialog(context, action)
    }

    private fun createSortDialog(context: Context) {
        DialogUtil.createSortDialog(context) { type ->
            activityViewModel.sortCards(type)
        }
    }
}