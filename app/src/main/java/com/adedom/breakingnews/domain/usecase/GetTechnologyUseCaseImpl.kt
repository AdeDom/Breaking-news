package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.TechnologyEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.TechnologyDataSource
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.data.repository.TechnologyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class GetTechnologyUseCaseImpl(
    private val dataSource: TechnologyDataSource,
    private val repository: TechnologyRepository,
) : GetTechnologyUseCase {

    override fun invoke(): Flow<TechnologyEntity> {
        return dataSource.getTechnologyFlow().map { mapTechnologyEntitySetDate(it) }
    }

    override fun mapTechnologyEntitySetDate(technologyEntityList: List<TechnologyEntity>): TechnologyEntity {
        val originFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val destinationFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        return TechnologyEntity(
            totalResults = if (technologyEntityList.isEmpty()) 0 else technologyEntityList[0].totalResults,
            articles = technologyEntityList.flatMap { it.articles }
                .map {
                    val publishedAt = try {
                        it.publishedAt?.let { publishedAt ->
                            val date = originFormat.parse(publishedAt)
                            date?.let { destinationFormat.format(date) }
                        }
                    } catch (e: Throwable) {
                        ""
                    }

                    ArticleDb(
                        id = it.id,
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        url = it.url,
                        urlToImage = it.urlToImage,
                        publishedAt = publishedAt,
                    )
                }
        )
    }

    override suspend fun callCategoryTechnology(): Resource<BreakingNewsResponse> {
        return repository.callCategoryTechnology()
    }

    override suspend fun callCategoryTechnologyNextPage(): Resource<BreakingNewsResponse> {
        val technologyList = dataSource.getTechnologyList()
        val page = (technologyList.flatMap { it.articles }.size / 20) + 1
        return repository.callCategoryTechnologyNextPage(page)
    }

    override suspend fun callCategoryTechnologySearch(query: String): Resource<BreakingNewsResponse> {
        return repository.callCategoryTechnologySearch(query)
    }

}
