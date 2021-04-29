package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.HealthEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow

interface GetHealthUseCase {

    operator fun invoke(): Flow<HealthEntity>

    fun mapHealthEntitySetDate(healthEntityList: List<HealthEntity>): HealthEntity

    suspend fun callCategoryHealth(): Resource<BreakingNewsResponse>

    suspend fun callCategoryHealthNextPage(): Resource<BreakingNewsResponse>

    suspend fun callCategoryHealthSearch(query: String): Resource<BreakingNewsResponse>

}
