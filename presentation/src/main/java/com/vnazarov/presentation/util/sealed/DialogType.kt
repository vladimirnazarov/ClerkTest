package com.vnazarov.presentation.util.sealed

sealed class DialogType {
    data object SortDialog: DialogType()
    data object SearchDialog: DialogType()
}