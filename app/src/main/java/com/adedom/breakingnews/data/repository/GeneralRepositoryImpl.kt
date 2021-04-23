package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.base.BaseRepository
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.GeneralDataSource

class GeneralRepositoryImpl(
    private val dataSource: GeneralDataSource,
) : BaseRepository(), GeneralRepository {

    override suspend fun callCategoryGeneral(
        country: String?
    ): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            dataSource.callBreakingNews(CategoryConstant.GENERAL, country)
        }
        if (resource is Resource.Success) {
            dataSource.deleteGeneral()
            val generalEntity = mapBreakingNewsResponseToGeneralEntity(resource.data)
            dataSource.saveGeneral(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryGeneralNextPage(
        country: String?,
        page: Int,
    ): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            dataSource.callBreakingNews(CategoryConstant.GENERAL, country, page = page)
        }
        if (resource is Resource.Success) {
            val generalEntity = mapBreakingNewsResponseToGeneralEntity(resource.data)
            dataSource.saveGeneral(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryGeneralSearch(
        country: String?,
        query: String
    ): Resource<BreakingNewsResponse> {
        val resource = safeApiCall {
            dataSource.callBreakingNews(CategoryConstant.GENERAL, country, query = query)
        }
        if (resource is Resource.Success) {
            dataSource.deleteGeneral()
            val generalEntity = mapBreakingNewsResponseToGeneralEntity(resource.data)
            dataSource.saveGeneral(generalEntity)
        }
        return resource
    }

    private fun mapBreakingNewsResponseToGeneralEntity(response: BreakingNewsResponse): GeneralEntity {
        return GeneralEntity(
            totalResults = response.totalResults,
            articles = response.articles.map {
                ArticleDb(
                    id = System.currentTimeMillis(),
                    author = it.author ?: it.source?.name,
                    title = it.title,
                    description = it.description,
                    urlToImage = it.urlToImage,
                    publishedAt = it.publishedAt,
                )
            }
        )
    }

}
