package com.timmerl.mementoguesser.presentation.utils

import android.content.Context
import androidx.annotation.StringRes

class StringWrapper private constructor(
    private val string: String?,
    @StringRes private val stringResId: Int = 0,
    private val args: ArrayList<Any>?
) {

    constructor(string: String) : this(string, 0, null)

    constructor(
        @StringRes stringResId: Int,
        stringVar: String
    ) : this(
        null,
        stringResId,
        arrayListOf(stringVar)
    )

    constructor(
        @StringRes stringResId: Int,
        intVar: Int
    ) : this(
        null,
        stringResId,
        arrayListOf(intVar)
    )

    constructor(
        @StringRes stringResId: Int,
        args: ArrayList<Any> = arrayListOf()
    ) : this(
        null,
        stringResId,
        args
    )

    fun resolve(context: Context): String {
        return when {
            string != null -> string
            args != null -> return context.getString(stringResId, *args.toArray())
            else -> context.getString(stringResId)
        }
    }
}
