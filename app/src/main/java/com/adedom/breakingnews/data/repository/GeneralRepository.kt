package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

interface GeneralRepository {

    suspend fun callBreakingNews(
        category: String,
        country: String? = null
    ): Resource<BreakingNewsResponse>

}
