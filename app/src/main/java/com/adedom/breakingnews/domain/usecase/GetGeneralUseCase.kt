package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.domain.CategoryConstant
import kotlinx.coroutines.flow.Flow

interface GetGeneralUseCase {

    operator fun invoke(): Flow<GeneralEntity>

    suspend fun callBreakingNews(
        category: String = CategoryConstant.GENERAL,
        country: String? = null
    ): Resource<BreakingNewsResponse>

}
