package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow

interface GetGeneralUseCase {

    operator fun invoke(): Flow<GeneralEntity>

    suspend fun callCategoryGeneral(): Resource<BreakingNewsResponse>

    suspend fun callCategoryGeneralNextPage(): Resource<BreakingNewsResponse>

    suspend fun callCategoryGeneralSearch(query: String): Resource<BreakingNewsResponse>

}
