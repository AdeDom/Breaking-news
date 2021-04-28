package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

interface TechnologyRepository {

    suspend fun callCategoryTechnology(): Resource<BreakingNewsResponse>

    suspend fun callCategoryTechnologyNextPage(page: Int): Resource<BreakingNewsResponse>

    suspend fun callCategoryTechnologySearch(query: String): Resource<BreakingNewsResponse>

}
