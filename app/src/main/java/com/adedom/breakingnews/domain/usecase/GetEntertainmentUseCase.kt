package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.EntertainmentEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow

interface GetEntertainmentUseCase {

    operator fun invoke(): Flow<EntertainmentEntity>

    fun mapEntertainmentEntitySetDate(entertainmentEntityList: List<EntertainmentEntity>): EntertainmentEntity

    suspend fun callCategoryEntertainment(): Resource<BreakingNewsResponse>

    suspend fun callCategoryEntertainmentNextPage(): Resource<BreakingNewsResponse>

    suspend fun callCategoryEntertainmentSearch(query: String): Resource<BreakingNewsResponse>

}
