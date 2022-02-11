package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.base.BaseRepository
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.HealthEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.BreakingNewsDataSource
import com.adedom.breakingnews.data.network.source.HealthDataSource
import com.adedom.breakingnews.data.sharedpreference.SettingPref

class HealthRepositoryImpl(
    private val healthDataSource: HealthDataSource,
    private val breakingNewsDataSource: BreakingNewsDataSource,
    private val settingPref: SettingPref,
) : BaseRepository(), HealthRepository {

    override suspend fun callCategoryHealth(): Resource<BreakingNewsResponse> {
        val resource = callApi {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.HEALTH, getCountry())
        }
        if (resource is Resource.Success) {
            val titleListDb = healthDataSource.getHealthList()
                .flatMap { it.articles }
                .map { it.title }
            val titleListApi = resource.data.articles.map { it.title }
            if (titleListDb != titleListApi) {
                healthDataSource.deleteHealth()
                val generalEntity = mapBreakingNewsResponseToHealthEntity(resource.data)
                healthDataSource.saveHealth(generalEntity)
            }
        }
        return resource
    }

    override suspend fun callCategoryHealthNextPage(page: Int): Resource<BreakingNewsResponse> {
        val resource = callApi {
            breakingNewsDataSource.callBreakingNews(
                CategoryConstant.HEALTH,
                getCountry(),
                page = page
            )
        }
        if (resource is Resource.Success) {
            val generalEntity = mapBreakingNewsResponseToHealthEntity(resource.data)
            healthDataSource.saveHealth(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryHealthSearch(query: String): Resource<BreakingNewsResponse> {
        val resource = callApi {
            breakingNewsDataSource.callBreakingNews(
                CategoryConstant.HEALTH,
                getCountry(),
                query = query
            )
        }
        if (resource is Resource.Success) {
            healthDataSource.deleteHealth()
            val generalEntity = mapBreakingNewsResponseToHealthEntity(resource.data)
            healthDataSource.saveHealth(generalEntity)
        }
        return resource
    }

    private fun getCountry(): String? = if (settingPref.isSearchOnlyThaiNews) "th" else null

    private fun mapBreakingNewsResponseToHealthEntity(response: BreakingNewsResponse): HealthEntity {
        return HealthEntity(
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
