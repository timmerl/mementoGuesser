package com.timmerl.mementoguesser.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Timmerman_Lyderic on 14/03/2021.
 */

class AnswerListConverters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromString(value: String): List<String> {
            val listType = object : TypeToken<List<String>>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromList(list: List<String>): String {
            val gson = Gson()
            return gson.toJson(list)
        }
    }
}