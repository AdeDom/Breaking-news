package com.adedom.breakingnews.data.network.api

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BreakingNewsApi {

    @GET("v2/top-headlines")
    suspend fun callBreakingNews(
        @Query("category") category: String,
        @Query("country") country: String? = null,
    ): BreakingNewsResponse

}
