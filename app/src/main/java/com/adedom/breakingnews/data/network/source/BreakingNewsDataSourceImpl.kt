package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.model.response.BreakingNewsResponse

class BreakingNewsDataSourceImpl(
    private val dataSourceProvider: DataSourceProvider,
) : BreakingNewsDataSource {

    override suspend fun callBreakingNews(
        category: String,
        country: String?,
        query: String?,
        page: Int?
    ): BreakingNewsResponse {
        return dataSourceProvider.getBreakingNews().callBreakingNews(category, country, query, page)
    }

}
