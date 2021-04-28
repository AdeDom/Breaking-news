package com.adedom.breakingnews.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "technology")
data class TechnologyEntity(
    val totalResults: Int? = null,
    val articles: List<ArticleDb> = emptyList(),
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
