package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.SportsEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow

interface GetSportsUseCase {

    operator fun invoke(): Flow<SportsEntity>

    fun mapSportsEntitySetDate(sportsEntityList: List<SportsEntity>): SportsEntity

    suspend fun callCategorySports(): Resource<BreakingNewsResponse>

    suspend fun callCategorySportsNextPage(): Resource<BreakingNewsResponse>

    suspend fun callCategorySportsSearch(query: String): Resource<BreakingNewsResponse>

}
