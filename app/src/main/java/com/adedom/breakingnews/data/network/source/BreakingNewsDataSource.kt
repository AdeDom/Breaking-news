package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

interface BreakingNewsDataSource {

    suspend fun callBreakingNews(
        category: String,
        country: String? = null,
        query: String? = null,
        page: Int? = null,
    ): BreakingNewsResponse

}
