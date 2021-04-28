package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.GeneralDataSource
import com.adedom.breakingnews.data.repository.GeneralRepository
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class GetGeneralUseCaseImpl(
    private val dataSource: GeneralDataSource,
    private val repository: GeneralRepository,
) : GetGeneralUseCase {

    override fun invoke(): Flow<GeneralEntity> {
        return dataSource.getGeneralFlow().map { mapGeneralEntitySetDate(it) }
    }

    override fun mapGeneralEntitySetDate(generalEntityList: List<GeneralEntity>): GeneralEntity {
        val originFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val destinationFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        return GeneralEntity(
            totalResults = if (generalEntityList.isEmpty()) 0 else generalEntityList[0].totalResults,
            articles = generalEntityList.flatMap { it.articles }
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

    override suspend fun callCategoryGeneral(): Resource<BreakingNewsResponse> {
        return repository.callCategoryGeneral()
    }

    override suspend fun callCategoryGeneralNextPage(): Resource<BreakingNewsResponse> {
        val generalList = dataSource.getGeneralList()
        val page = (generalList.flatMap { it.articles }.size / 20) + 1
        return repository.callCategoryGeneralNextPage(page)
    }

    override suspend fun callCategoryGeneralSearch(query: String): Resource<BreakingNewsResponse> {
        return repository.callCategoryGeneralSearch(query)
    }

}
