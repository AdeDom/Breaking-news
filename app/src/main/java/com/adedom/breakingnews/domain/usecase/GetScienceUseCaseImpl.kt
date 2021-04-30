package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.ScienceEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.ScienceDataSource
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.data.repository.ScienceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class GetScienceUseCaseImpl(
    private val dataSource: ScienceDataSource,
    private val repository: ScienceRepository,
) : GetScienceUseCase {

    override fun invoke(): Flow<ScienceEntity> {
        return dataSource.getScienceFlow().map { mapScienceEntitySetDate(it) }
    }

    override fun mapScienceEntitySetDate(scienceEntityList: List<ScienceEntity>): ScienceEntity {
        val originFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val destinationFormat = SimpleDateFormat("d/M/yyyy H:m", Locale.getDefault())
        return ScienceEntity(
            totalResults = if (scienceEntityList.isEmpty()) 0 else scienceEntityList[0].totalResults,
            articles = scienceEntityList.flatMap { it.articles }
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

    override suspend fun callCategoryScience(): Resource<BreakingNewsResponse> {
        return repository.callCategoryScience()
    }

    override suspend fun callCategoryScienceNextPage(): Resource<BreakingNewsResponse> {
        val scienceList = dataSource.getScienceList()
        val page = (scienceList.flatMap { it.articles }.size / 20) + 1
        return repository.callCategoryScienceNextPage(page)
    }

    override suspend fun callCategoryScienceSearch(query: String): Resource<BreakingNewsResponse> {
        return repository.callCategoryScienceSearch(query)
    }

}
