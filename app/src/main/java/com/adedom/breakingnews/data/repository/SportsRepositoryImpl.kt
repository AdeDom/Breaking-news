package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.base.BaseRepository
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.SportsEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.BreakingNewsDataSource
import com.adedom.breakingnews.data.network.source.SportsDataSource
import com.adedom.breakingnews.data.sharedpreference.SettingPref

class SportsRepositoryImpl(
    private val sportsDataSource: SportsDataSource,
    private val breakingNewsDataSource: BreakingNewsDataSource,
    private val settingPref: SettingPref,
) : BaseRepository(), SportsRepository {

    override suspend fun callCategorySports(): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.SPORTS, getCountry())
        }
        if (resource is Resource.Success) {
            sportsDataSource.deleteSports()
            val generalEntity = mapBreakingNewsResponseToSportsEntity(resource.data)
            sportsDataSource.saveSports(generalEntity)
        }
        return resource
    }

    override suspend fun callCategorySportsNextPage(page: Int): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.SPORTS, getCountry(), page = page)
        }
        if (resource is Resource.Success) {
            val generalEntity = mapBreakingNewsResponseToSportsEntity(resource.data)
            sportsDataSource.saveSports(generalEntity)
        }
        return resource
    }

    override suspend fun callCategorySportsSearch(query: String): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.SPORTS, getCountry(), query = query)
        }
        if (resource is Resource.Success) {
            sportsDataSource.deleteSports()
            val generalEntity = mapBreakingNewsResponseToSportsEntity(resource.data)
            sportsDataSource.saveSports(generalEntity)
        }
        return resource
    }

    private fun getCountry(): String? = if (settingPref.isSearchOnlyThaiNews) "th" else null

    private fun mapBreakingNewsResponseToSportsEntity(response: BreakingNewsResponse): SportsEntity {
        return SportsEntity(
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
