package com.vnazarov.presentation.util.sealed

sealed class SortAction {
    data object SortByClassUp: SortAction()
    data object SortByClassDown: SortAction()
    data object SortByCostUp: SortAction()
    data object SortByCostDown: SortAction()
}