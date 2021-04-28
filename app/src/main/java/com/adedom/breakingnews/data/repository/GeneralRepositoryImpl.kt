package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.base.BaseRepository
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.GeneralDataSource
import com.adedom.breakingnews.data.sharedpreference.SettingPref

class GeneralRepositoryImpl(
    private val dataSource: GeneralDataSource,
    private val settingPref: SettingPref,
) : BaseRepository(), GeneralRepository {

    override suspend fun callCategoryGeneral(): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            dataSource.callBreakingNews(CategoryConstant.GENERAL, getCountry())
        }
        if (resource is Resource.Success) {
            dataSource.deleteGeneral()
            val generalEntity = mapBreakingNewsResponseToGeneralEntity(resource.data)
            dataSource.saveGeneral(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryGeneralNextPage(page: Int): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            dataSource.callBreakingNews(CategoryConstant.GENERAL, getCountry(), page = page)
        }
        if (resource is Resource.Success) {
            val generalEntity = mapBreakingNewsResponseToGeneralEntity(resource.data)
            dataSource.saveGeneral(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryGeneralSearch(query: String): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            dataSource.callBreakingNews(CategoryConstant.GENERAL, getCountry(), query = query)
        }
        if (resource is Resource.Success) {
            dataSource.deleteGeneral()
            val generalEntity = mapBreakingNewsResponseToGeneralEntity(resource.data)
            dataSource.saveGeneral(generalEntity)
        }
        return resource
    }

    private fun getCountry(): String? = if (settingPref.isSearchOnlyThaiNews) "th" else null

    private fun mapBreakingNewsResponseToGeneralEntity(response: BreakingNewsResponse): GeneralEntity {
        return GeneralEntity(
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
