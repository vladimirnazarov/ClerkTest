package com.vnazarov.presentation.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.InputType
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.vnazarov.presentation.databinding.DialogSearchBinding
import com.vnazarov.presentation.databinding.DialogSortBinding
import com.vnazarov.presentation.util.sealed.SortAction

object DialogUtil {

    fun createSortDialog(
        context: Context,
        onSort: (SortAction) -> Unit
    ) {
        val dialog = setupDialog(context)
        val dialogBinding = DialogSortBinding.inflate(LayoutInflater.from(context))

        dialog.setContentView(dialogBinding.root)
        setupDialogWindow(dialog, context)

        dialogBinding.apply {
            dialogSortByClassUpAction.setOnClickListener {
                onSort(SortAction.SortByClassUp)
                dialog.dismiss()
            }

            dialogSortByClassDownAction.setOnClickListener {
                dialog.dismiss()
                onSort(SortAction.SortByClassDown)
            }

            dialogSortByCostUpAction.setOnClickListener {
                dialog.dismiss()
                onSort(SortAction.SortByCostUp)
            }

            dialogSortByCostDownAction.setOnClickListener {
                dialog.dismiss()
                onSort(SortAction.SortByCostDown)
            }
        }

        dialog.show()
    }

    fun createSearchDialog(
        context: Context,
        onSearch: (String) -> Unit
    ) {
        val dialog = setupDialog(context)
        val dialogBinding = DialogSearchBinding.inflate(LayoutInflater.from(context))

        dialogBinding.dialogSearchEditText.apply {
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            inputType = InputType.TYPE_CLASS_TEXT

            requestFocus()
            post {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = text.toString()

                    dialog.dismiss()
                    onSearch(query)

                    true
                } else false
            }
        }

        dialog.setContentView(dialogBinding.root)
        setupDialogWindow(dialog, context)

        dialog.show()
    }

    private fun setupDialog(context: Context): Dialog {
        return Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun setupDialogWindow(dialog: Dialog, context: Context) {
        val width = context.resources.displayMetrics.widthPixels
        val layoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window?.attributes)
            this.width = width
        }
        dialog.window?.attributes = layoutParams
    }
}