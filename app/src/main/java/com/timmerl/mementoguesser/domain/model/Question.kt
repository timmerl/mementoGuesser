package com.timmerl.mementoguesser.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class Question(
    val id: Int,
    val question: String,
    val answer: String
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
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(question)
        parcel.writeString(answer)
    }

    override fun describeContents() = 0
}