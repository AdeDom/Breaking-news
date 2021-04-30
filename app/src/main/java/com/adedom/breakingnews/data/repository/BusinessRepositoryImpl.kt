package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.base.BaseRepository
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.BusinessEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.BreakingNewsDataSource
import com.adedom.breakingnews.data.network.source.BusinessDataSource
import com.adedom.breakingnews.data.sharedpreference.SettingPref

class BusinessRepositoryImpl(
    private val businessDataSource: BusinessDataSource,
    private val breakingNewsDataSource: BreakingNewsDataSource,
    private val settingPref: SettingPref,
) : BaseRepository(), BusinessRepository {

    override suspend fun callCategoryBusiness(): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.BUSINESS, getCountry())
        }
        if (resource is Resource.Success) {
            val titleListDb = businessDataSource.getBusinessList()
                .flatMap { it.articles }
                .map { it.title }
            val titleListApi = resource.data.articles.map { it.title }
            if (titleListDb != titleListApi) {
                businessDataSource.deleteBusiness()
                val generalEntity = mapBreakingNewsResponseToBusinessEntity(resource.data)
                businessDataSource.saveBusiness(generalEntity)
            }
        }
        return resource
    }

    override suspend fun callCategoryBusinessNextPage(page: Int): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.BUSINESS, getCountry(), page = page)
        }
        if (resource is Resource.Success) {
            val generalEntity = mapBreakingNewsResponseToBusinessEntity(resource.data)
            businessDataSource.saveBusiness(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryBusinessSearch(query: String): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.BUSINESS, getCountry(), query = query)
        }
        if (resource is Resource.Success) {
            businessDataSource.deleteBusiness()
            val generalEntity = mapBreakingNewsResponseToBusinessEntity(resource.data)
            businessDataSource.saveBusiness(generalEntity)
        }
        return resource
    }

    private fun getCountry(): String? = if (settingPref.isSearchOnlyThaiNews) "th" else null

    private fun mapBreakingNewsResponseToBusinessEntity(response: BreakingNewsResponse): BusinessEntity {
        return BusinessEntity(
            totalResults = response.totalResults,
            articles = response.articles.map {
                ArticleDb(
                    id = System.currentTimeMillis(),
                    author = it.author ?: it.source?.name,
                    title = it.title,
                    description = it.description,
                    url = it.url,
                    urlToImage = it.urlToImage,
                    publishedAt = it.publishedAt,
                )
            }
        )
    }

}
