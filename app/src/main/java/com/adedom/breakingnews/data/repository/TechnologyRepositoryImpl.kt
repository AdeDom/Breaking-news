package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.base.BaseRepository
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.TechnologyEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.BreakingNewsDataSource
import com.adedom.breakingnews.data.network.source.TechnologyDataSource
import com.adedom.breakingnews.data.sharedpreference.SettingPref

class TechnologyRepositoryImpl(
    private val technologyDataSource: TechnologyDataSource,
    private val breakingNewsDataSource: BreakingNewsDataSource,
    private val settingPref: SettingPref,
) : BaseRepository(), TechnologyRepository {

    override suspend fun callCategoryTechnology(): Resource<BreakingNewsResponse> {
        val resource = callApi {
            breakingNewsDataSource.callBreakingNews(CategoryConstant.TECHNOLOGY, getCountry())
        }
        if (resource is Resource.Success) {
            val titleListDb = technologyDataSource.getTechnologyList()
                .flatMap { it.articles }
                .map { it.title }
            val titleListApi = resource.data.articles.map { it.title }
            if (titleListDb != titleListApi) {
                technologyDataSource.deleteTechnology()
                val generalEntity = mapBreakingNewsResponseToTechnologyEntity(resource.data)
                technologyDataSource.saveTechnology(generalEntity)
            }
        }
        return resource
    }

    override suspend fun callCategoryTechnologyNextPage(page: Int): Resource<BreakingNewsResponse> {
        val resource = callApi {
            breakingNewsDataSource.callBreakingNews(
                CategoryConstant.TECHNOLOGY,
                getCountry(),
                page = page
            )
        }
        if (resource is Resource.Success) {
            val generalEntity = mapBreakingNewsResponseToTechnologyEntity(resource.data)
            technologyDataSource.saveTechnology(generalEntity)
        }
        return resource
    }

    override suspend fun callCategoryTechnologySearch(query: String): Resource<BreakingNewsResponse> {
        val resource = callApi {
            breakingNewsDataSource.callBreakingNews(
                CategoryConstant.TECHNOLOGY,
                getCountry(),
                query = query
            )
        }
        if (resource is Resource.Success) {
            technologyDataSource.deleteTechnology()
            val generalEntity = mapBreakingNewsResponseToTechnologyEntity(resource.data)
            technologyDataSource.saveTechnology(generalEntity)
        }
        return resource
    }

    private fun getCountry(): String? = if (settingPref.isSearchOnlyThaiNews) "th" else null

    private fun mapBreakingNewsResponseToTechnologyEntity(response: BreakingNewsResponse): TechnologyEntity {
        return TechnologyEntity(
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
