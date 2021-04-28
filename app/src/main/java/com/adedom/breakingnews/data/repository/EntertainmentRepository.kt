package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

interface EntertainmentRepository {

    suspend fun callCategoryEntertainment(): Resource<BreakingNewsResponse>

    suspend fun callCategoryEntertainmentNextPage(page: Int): Resource<BreakingNewsResponse>

    suspend fun callCategoryEntertainmentSearch(query: String): Resource<BreakingNewsResponse>

}
