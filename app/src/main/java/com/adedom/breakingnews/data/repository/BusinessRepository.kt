package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

interface BusinessRepository {

    suspend fun callCategoryBusiness(): Resource<BreakingNewsResponse>

    suspend fun callCategoryBusinessNextPage(page: Int): Resource<BreakingNewsResponse>

    suspend fun callCategoryBusinessSearch(query: String): Resource<BreakingNewsResponse>

}
