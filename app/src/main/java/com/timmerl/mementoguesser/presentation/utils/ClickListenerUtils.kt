package com.timmerl.mementoguesser.presentation.utils

import android.os.SystemClock
import android.view.View

/**
 * Created by Timmerman_Lyderic on 14/03/2021.
 */

class SafeClickListener(
    private val interval: Int = 250,
    private val onSafeClick: (View) -> Unit
) : View.OnClickListener {

    private var lastClickTime: Long = 0L

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < interval) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()
        onSafeClick(v)
    }
}

fun View.setOnSafeClickListener(
    onSafeClick: (View) -> Unit
) = setOnClickListener(SafeClickListener { v ->
    onSafeClick(v)
})

fun View.setOnSafeClickListener(
    interval: Int,
    onSafeClick: (View) -> Unit
) = setOnClickListener(SafeClickListener(
    interval = interval,
    onSafeClick = { v ->
        onSafeClick(v)
    }
))