package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.HealthEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.HealthDataSource
import com.adedom.breakingnews.data.repository.HealthRepository
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class GetHealthUseCaseImpl(
    private val dataSource: HealthDataSource,
    private val repository: HealthRepository,
) : GetHealthUseCase {

    override fun invoke(): Flow<HealthEntity> {
        return dataSource.getHealthFlow().map { mapHealthEntitySetDate(it) }
    }

    override fun mapHealthEntitySetDate(healthEntityList: List<HealthEntity>): HealthEntity {
        val originFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val destinationFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        return HealthEntity(
            totalResults = if (healthEntityList.isEmpty()) 0 else healthEntityList[0].totalResults,
            articles = healthEntityList.flatMap { it.articles }
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

    override suspend fun callCategoryHealth(): Resource<BreakingNewsResponse> {
        return repository.callCategoryHealth()
    }

    override suspend fun callCategoryHealthNextPage(): Resource<BreakingNewsResponse> {
        val healthList = dataSource.getHealthList()
        val page = (healthList.flatMap { it.articles }.size / 20) + 1
        return repository.callCategoryHealthNextPage(page)
    }

    override suspend fun callCategoryHealthSearch(query: String): Resource<BreakingNewsResponse> {
        return repository.callCategoryHealthSearch(query)
    }

}
