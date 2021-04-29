package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.SportsEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.SportsDataSource
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.data.repository.SportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class GetSportsUseCaseImpl(
    private val dataSource: SportsDataSource,
    private val repository: SportsRepository,
) : GetSportsUseCase {

    override fun invoke(): Flow<SportsEntity> {
        return dataSource.getSportsFlow().map { mapSportsEntitySetDate(it) }
    }

    override fun mapSportsEntitySetDate(sportsEntityList: List<SportsEntity>): SportsEntity {
        val originFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val destinationFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        return SportsEntity(
            totalResults = if (sportsEntityList.isEmpty()) 0 else sportsEntityList[0].totalResults,
            articles = sportsEntityList.flatMap { it.articles }
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

    override suspend fun callCategorySports(): Resource<BreakingNewsResponse> {
        return repository.callCategorySports()
    }

    override suspend fun callCategorySportsNextPage(): Resource<BreakingNewsResponse> {
        val sportsList = dataSource.getSportsList()
        val page = (sportsList.flatMap { it.articles }.size / 20) + 1
        return repository.callCategorySportsNextPage(page)
    }

    override suspend fun callCategorySportsSearch(query: String): Resource<BreakingNewsResponse> {
        return repository.callCategorySportsSearch(query)
    }

}
