package com.timmerl.mementoguesser.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

data class Question(
    val id: Int,
    val question: String,
    val answer: String,
    val isPlayable: Boolean
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Question> {
            override fun createFromParcel(parcel: Parcel) = Question(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Question>(size)
        }
    }

    private constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        question = parcel.readString() ?: "",
        answer = parcel.readString() ?: "",
        isPlayable = parcel.readInt() != 0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(question)
        parcel.writeString(answer)
        parcel.writeInt(if (isPlayable) 1 else 0)
    }

    override fun describeContents() = 0
}