package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.BusinessEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.BusinessDataSource
import com.adedom.breakingnews.data.repository.BusinessRepository
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class GetBusinessUseCaseImpl(
    private val dataSource: BusinessDataSource,
    private val repository: BusinessRepository,
) : GetBusinessUseCase {

    override fun invoke(): Flow<BusinessEntity> {
        return dataSource.getBusinessFlow().map { mapBusinessEntitySetDate(it) }
    }

    override fun mapBusinessEntitySetDate(businessEntityList: List<BusinessEntity>): BusinessEntity {
        val originFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val destinationFormat = SimpleDateFormat("d/M/yyyy H:m", Locale.getDefault())
        return BusinessEntity(
            totalResults = if (businessEntityList.isEmpty()) 0 else businessEntityList[0].totalResults,
            articles = businessEntityList.flatMap { it.articles }
                .distinctBy { it.title }
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

    override suspend fun callCategoryBusiness(): Resource<BreakingNewsResponse> {
        return repository.callCategoryBusiness()
    }

    override suspend fun callCategoryBusinessNextPage(): Resource<BreakingNewsResponse> {
        val businessList = dataSource.getBusinessList()
        val page = (businessList.flatMap { it.articles }.size / 20) + 1
        return repository.callCategoryBusinessNextPage(page)
    }

    override suspend fun callCategoryBusinessSearch(query: String): Resource<BreakingNewsResponse> {
        return repository.callCategoryBusinessSearch(query)
    }

}
