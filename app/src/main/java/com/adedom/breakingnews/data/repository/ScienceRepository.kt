package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

interface ScienceRepository {

    suspend fun callCategoryScience(): Resource<BreakingNewsResponse>

    suspend fun callCategoryScienceNextPage(page: Int): Resource<BreakingNewsResponse>

    suspend fun callCategoryScienceSearch(query: String): Resource<BreakingNewsResponse>

}
