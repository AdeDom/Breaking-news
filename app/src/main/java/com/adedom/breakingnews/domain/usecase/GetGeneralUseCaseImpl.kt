package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.network.source.GeneralDataSource
import com.adedom.breakingnews.data.repository.GeneralRepository
import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.flow.Flow

class GetGeneralUseCaseImpl(
    private val dataSource: GeneralDataSource,
    private val repository: GeneralRepository,
) : GetGeneralUseCase {

    override fun invoke(): Flow<GeneralEntity> {
        return dataSource.getGeneralFlow()
    }

    override suspend fun callBreakingNews(
        category: String,
        country: String?
    ): Resource<BreakingNewsResponse> {
        return repository.callBreakingNews(category, country)
    }

}
