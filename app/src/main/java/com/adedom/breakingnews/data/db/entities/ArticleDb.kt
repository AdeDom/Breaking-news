package com.adedom.breakingnews.data.db.entities

data class ArticleDb(
    val id: Long,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
)
