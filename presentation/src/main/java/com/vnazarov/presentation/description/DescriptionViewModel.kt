package com.vnazarov.presentation.description

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vnazarov.presentation.MainViewModel

class DescriptionViewModel(
    private val activityViewModel: MainViewModel
): ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory(private val activityViewModel: MainViewModel): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DescriptionViewModel(activityViewModel) as T
        }
    }

    fun getListByType(listType: String) =
        if (listType == LIST_TYPE_FAVOURITE) activityViewModel.favouriteCards.value!!
        else activityViewModel.allCards.value!!

    companion object {
        private const val LIST_TYPE_FAVOURITE = "list_type_favourite"
    }
}