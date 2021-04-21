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

    private fun mapGeneralEntitySetDate(generalEntity: GeneralEntity?): GeneralEntity {
        val originFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val destinationFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        return GeneralEntity(
            totalResults = generalEntity?.totalResults,
            articles = generalEntity?.articles?.map {
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
                    urlToImage = it.urlToImage,
                    publishedAt = publishedAt,
                )
            }?: emptyList()
        )
    }

    override suspend fun callBreakingNews(
        category: String,
        country: String?
    ): Resource<BreakingNewsResponse> {
        return repository.callBreakingNews(category, country)
    }

}
