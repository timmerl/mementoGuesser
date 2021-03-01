package com.timmerl.mementoguesser.presentation.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


/**
 * Created by Timmerman_Lyderic on 01/03/2021.
 */
fun View.showKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}


fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}

fun Context.hideKeyboard(view: View) {
    view.hideKeyboard()
}