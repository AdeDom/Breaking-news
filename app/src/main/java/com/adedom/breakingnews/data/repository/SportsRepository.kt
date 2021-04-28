package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

interface SportsRepository {

    suspend fun callCategorySports(): Resource<BreakingNewsResponse>

    suspend fun callCategorySportsNextPage(page: Int): Resource<BreakingNewsResponse>

    suspend fun callCategorySportsSearch(query: String): Resource<BreakingNewsResponse>

}
