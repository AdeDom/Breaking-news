package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.base.BaseRepository
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.ScienceEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.BreakingNewsDataSource
import com.adedom.breakingnews.data.network.source.ScienceDataSource
import com.adedom.breakingnews.data.sharedpreference.SettingPref

class ScienceRepositoryImpl(
    private val scienceDataSource: ScienceDataSource,
    private val breakingNewsDataSource: BreakingNewsDataSource,
    private val settingPref: SettingPref,
) : BaseRepository(), ScienceRepository {

    override suspend fun callCategoryScience(): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.SCIENCE, getCountry())
        }
        if (resource is Resource.Success) {
            scienceDataSource.deleteScience()
            val generalEntity = mapBreakingNewsResponseToScienceEntity(resource.data)
            scienceDataSource.saveScience(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryScienceNextPage(page: Int): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.SCIENCE, getCountry(), page = page)
        }
        if (resource is Resource.Success) {
            val generalEntity = mapBreakingNewsResponseToScienceEntity(resource.data)
            scienceDataSource.saveScience(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryScienceSearch(query: String): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.SCIENCE, getCountry(), query = query)
        }
        if (resource is Resource.Success) {
            scienceDataSource.deleteScience()
            val generalEntity = mapBreakingNewsResponseToScienceEntity(resource.data)
            scienceDataSource.saveScience(generalEntity)
        }
        return resource
    }

    private fun getCountry(): String? = if (settingPref.isSearchOnlyThaiNews) "th" else null

    private fun mapBreakingNewsResponseToScienceEntity(response: BreakingNewsResponse): ScienceEntity {
        return ScienceEntity(
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
