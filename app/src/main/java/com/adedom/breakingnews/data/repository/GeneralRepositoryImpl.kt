package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.base.BaseRepository
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.BreakingNewsDataSource
import com.adedom.breakingnews.data.network.source.GeneralDataSource
import com.adedom.breakingnews.data.sharedpreference.SettingPref

class GeneralRepositoryImpl(
    private val generalDataSource: GeneralDataSource,
    private val breakingNewsDataSource: BreakingNewsDataSource,
    private val settingPref: SettingPref,
) : BaseRepository(), GeneralRepository {

    override suspend fun callCategoryGeneral(): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.GENERAL, getCountry())
        }
        if (resource is Resource.Success) {
            val titleListDb = generalDataSource.getGeneralList()
                .flatMap { it.articles }
                .map { it.title }
            val titleListApi = resource.data.articles.map { it.title }
            if (titleListDb != titleListApi) {
                generalDataSource.deleteGeneral()
                val generalEntity = mapBreakingNewsResponseToGeneralEntity(resource.data)
                generalDataSource.saveGeneral(generalEntity)
            }
        }
        return resource
    }

    override suspend fun callCategoryGeneralNextPage(page: Int): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.GENERAL, getCountry(), page = page)
        }
        if (resource is Resource.Success) {
            val generalEntity = mapBreakingNewsResponseToGeneralEntity(resource.data)
            generalDataSource.saveGeneral(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryGeneralSearch(query: String): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.GENERAL, getCountry(), query = query)
        }
        if (resource is Resource.Success) {
            generalDataSource.deleteGeneral()
            val generalEntity = mapBreakingNewsResponseToGeneralEntity(resource.data)
            generalDataSource.saveGeneral(generalEntity)
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
