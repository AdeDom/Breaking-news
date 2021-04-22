package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

interface GeneralRepository {

    suspend fun callCategoryGeneral(
        category: String,
        country: String? = null
    ): Resource<BreakingNewsResponse>

    suspend fun callCategoryGeneralNextPage(
        category: String,
        country: String? = null,
        page: Int,
    ): Resource<BreakingNewsResponse>

}
