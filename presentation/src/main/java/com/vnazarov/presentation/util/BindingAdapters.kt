package com.vnazarov.presentation.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:onClick")
    fun setOnClick(view: View, action: LiveData<() -> Unit>?) {
        view.setOnClickListener {
            action?.value?.invoke()
        }
    }
}