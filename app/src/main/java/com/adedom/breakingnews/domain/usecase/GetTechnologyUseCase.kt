package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.TechnologyEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow

interface GetTechnologyUseCase {

    operator fun invoke(): Flow<TechnologyEntity>

    fun mapTechnologyEntitySetDate(technologyEntityList: List<TechnologyEntity>): TechnologyEntity

    suspend fun callCategoryTechnology(): Resource<BreakingNewsResponse>

    suspend fun callCategoryTechnologyNextPage(): Resource<BreakingNewsResponse>

    suspend fun callCategoryTechnologySearch(query: String): Resource<BreakingNewsResponse>

}
