package com.vnazarov.presentation.util.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.makeToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}