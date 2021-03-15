package com.timmerl.mementoguesser.presentation.model

import android.os.Parcel
import android.os.Parcelable


/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

data class QuestionUiModel(
    val mementoId: Long,
    val answerId: Long,
    val question: String,
    val answer: String,
    val isPlayable: Boolean,
    val showMenu: Boolean,
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<QuestionUiModel> {
            override fun createFromParcel(parcel: Parcel) = QuestionUiModel(parcel)
            override fun newArray(size: Int) = arrayOfNulls<QuestionUiModel>(size)
        }
    }

    private constructor(parcel: Parcel) : this(
        mementoId = parcel.readLong(),
        answerId = parcel.readLong(),
        question = parcel.readString() ?: "",
        answer = parcel.readString() ?: "",
        isPlayable = parcel.readInt() != 0,
        showMenu = parcel.readInt() != 0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(mementoId)
        parcel.writeLong(answerId)
        parcel.writeString(question)
        parcel.writeString(answer)
        parcel.writeInt(if (isPlayable) 1 else 0)
        parcel.writeInt(if (showMenu) 1 else 0)
    }

    override fun describeContents() = 0
}