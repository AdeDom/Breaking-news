package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.ScienceEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow

interface GetScienceUseCase {

    operator fun invoke(): Flow<ScienceEntity>

    fun mapScienceEntitySetDate(scienceEntityList: List<ScienceEntity>): ScienceEntity

    suspend fun callCategoryScience(): Resource<BreakingNewsResponse>

    suspend fun callCategoryScienceNextPage(): Resource<BreakingNewsResponse>

    suspend fun callCategoryScienceSearch(query: String): Resource<BreakingNewsResponse>

}
