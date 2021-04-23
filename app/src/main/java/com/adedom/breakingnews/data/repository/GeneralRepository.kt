package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

interface GeneralRepository {

    suspend fun callCategoryGeneral(country: String? = null): Resource<BreakingNewsResponse>

    suspend fun callCategoryGeneralNextPage(
        country: String? = null,
        page: Int,
    ): Resource<BreakingNewsResponse>

    suspend fun callCategoryGeneralSearch(
        country: String? = null,
        query: String,
    ): Resource<BreakingNewsResponse>

}
