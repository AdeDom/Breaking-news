package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.base.BaseRepository
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.EntertainmentEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.BreakingNewsDataSource
import com.adedom.breakingnews.data.network.source.EntertainmentDataSource
import com.adedom.breakingnews.data.sharedpreference.SettingPref

class EntertainmentRepositoryImpl(
    private val entertainmentDataSource: EntertainmentDataSource,
    private val breakingNewsDataSource: BreakingNewsDataSource,
    private val settingPref: SettingPref,
) : BaseRepository(), EntertainmentRepository {

    override suspend fun callCategoryEntertainment(): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.ENTERTAINMENT, getCountry())
        }
        if (resource is Resource.Success) {
            entertainmentDataSource.deleteEntertainment()
            val generalEntity = mapBreakingNewsResponseToEntertainmentEntity(resource.data)
            entertainmentDataSource.saveEntertainment(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryEntertainmentNextPage(page: Int): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.ENTERTAINMENT, getCountry(), page = page)
        }
        if (resource is Resource.Success) {
            val generalEntity = mapBreakingNewsResponseToEntertainmentEntity(resource.data)
            entertainmentDataSource.saveEntertainment(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryEntertainmentSearch(query: String): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.ENTERTAINMENT, getCountry(), query = query)
        }
        if (resource is Resource.Success) {
            entertainmentDataSource.deleteEntertainment()
            val generalEntity = mapBreakingNewsResponseToEntertainmentEntity(resource.data)
            entertainmentDataSource.saveEntertainment(generalEntity)
        }
        return resource
    }

    private fun getCountry(): String? = if (settingPref.isSearchOnlyThaiNews) "th" else null

    private fun mapBreakingNewsResponseToEntertainmentEntity(response: BreakingNewsResponse): EntertainmentEntity {
        return EntertainmentEntity(
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
