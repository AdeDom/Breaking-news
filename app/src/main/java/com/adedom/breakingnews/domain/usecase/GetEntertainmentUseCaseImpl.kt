package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.EntertainmentEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.EntertainmentDataSource
import com.adedom.breakingnews.data.repository.EntertainmentRepository
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class GetEntertainmentUseCaseImpl(
    private val dataSource: EntertainmentDataSource,
    private val repository: EntertainmentRepository,
) : GetEntertainmentUseCase {

    override fun invoke(): Flow<EntertainmentEntity> {
        return dataSource.getEntertainmentFlow().map { mapEntertainmentEntitySetDate(it) }
    }

    override fun mapEntertainmentEntitySetDate(entertainmentEntityList: List<EntertainmentEntity>): EntertainmentEntity {
        val originFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val destinationFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        return EntertainmentEntity(
            totalResults = if (entertainmentEntityList.isEmpty()) 0 else entertainmentEntityList[0].totalResults,
            articles = entertainmentEntityList.flatMap { it.articles }
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

    override suspend fun callCategoryEntertainment(): Resource<BreakingNewsResponse> {
        return repository.callCategoryEntertainment()
    }

    override suspend fun callCategoryEntertainmentNextPage(): Resource<BreakingNewsResponse> {
        val entertainmentList = dataSource.getEntertainmentList()
        val page = (entertainmentList.flatMap { it.articles }.size / 20) + 1
        return repository.callCategoryEntertainmentNextPage(page)
    }

    override suspend fun callCategoryEntertainmentSearch(query: String): Resource<BreakingNewsResponse> {
        return repository.callCategoryEntertainmentSearch(query)
    }

}
