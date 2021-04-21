package com.adedom.breakingnews.data.model.response

data class BreakingNewsResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<ArticleData> = emptyList(),
)
