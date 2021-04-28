package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

interface HealthRepository {

    suspend fun callCategoryHealth(): Resource<BreakingNewsResponse>

    suspend fun callCategoryHealthNextPage(page: Int): Resource<BreakingNewsResponse>

    suspend fun callCategoryHealthSearch(query: String): Resource<BreakingNewsResponse>

}
