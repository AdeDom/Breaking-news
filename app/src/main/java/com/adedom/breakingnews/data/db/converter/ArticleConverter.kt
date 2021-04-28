package com.adedom.breakingnews.data.db.converter

import androidx.room.TypeConverter
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.google.gson.Gson

class ArticleConverter {

    @TypeConverter
    fun listToJson(value: List<ArticleDb>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<ArticleDb> {
        return Gson().fromJson(value, Array<ArticleDb>::class.java).toList()
    }

}
