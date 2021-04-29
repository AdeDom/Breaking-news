package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.BusinessEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow

interface GetBusinessUseCase {

    operator fun invoke(): Flow<BusinessEntity>

    fun mapBusinessEntitySetDate(businessEntityList: List<BusinessEntity>): BusinessEntity

    suspend fun callCategoryBusiness(): Resource<BreakingNewsResponse>

    suspend fun callCategoryBusinessNextPage(): Resource<BreakingNewsResponse>

    suspend fun callCategoryBusinessSearch(query: String): Resource<BreakingNewsResponse>

}
