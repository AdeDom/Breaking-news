package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

interface GeneralRepository {

    suspend fun callCategoryGeneral(): Resource<BreakingNewsResponse>

    suspend fun callCategoryGeneralNextPage(page: Int): Resource<BreakingNewsResponse>

    suspend fun callCategoryGeneralSearch(query: String): Resource<BreakingNewsResponse>

}
