package com.adedom.breakingnews.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health")
data class HealthEntity(
    val totalResults: Int? = null,
    val articles: List<ArticleDb> = emptyList(),
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}