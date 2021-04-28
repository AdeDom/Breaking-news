package com.adedom.breakingnews.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entertainment")
data class EntertainmentEntity(
    val totalResults: Int? = null,
    val articles: List<ArticleDb> = emptyList(),
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
