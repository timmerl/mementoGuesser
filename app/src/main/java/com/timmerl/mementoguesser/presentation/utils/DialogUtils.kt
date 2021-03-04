package com.timmerl.mementoguesser.presentation.utils

import android.app.Activity
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.timmerl.mementoguesser.R

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

fun Activity.showAlertWithEditText(
    title: String,
    positiveButtonText: String,
    action: (String) -> Unit
) {
    AlertDialog.Builder(this).apply {
        setTitle(title)
        val view = layoutInflater.inflate(R.layout.edit_text_dialog, null)
        val editText = view.findViewById<EditText>(R.id.dialog_edit_text)
        setView(view)
        setPositiveButton(positiveButtonText) { _, _ ->
            action.invoke(editText.text.toString())
        }
        setOnDismissListener { view.hideKeyboard() }
        view.showKeyboard()
    }.create().show()
}
