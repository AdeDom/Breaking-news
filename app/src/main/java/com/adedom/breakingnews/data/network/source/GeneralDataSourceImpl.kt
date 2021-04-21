package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import kotlinx.coroutines.flow.Flow

class GeneralDataSourceImpl(
    private val db: AppDatabase,
    private val dataSourceProvider: DataSourceProvider,
) : GeneralDataSource {

    override suspend fun saveGeneral(generalEntity: GeneralEntity) {
        return db.getGeneralDao().saveGeneral(generalEntity)
    }

    override fun getGeneralFlow(): Flow<GeneralEntity?> {
        return db.getGeneralDao().getGeneralFlow()
    }

    override suspend fun deleteGeneral() {
        return db.getGeneralDao().deleteGeneral()
    }

    override suspend fun callBreakingNews(
        category: String,
        country: String?
    ): BreakingNewsResponse {
        return dataSourceProvider.getBreakingNews().callBreakingNews(category, country)
    }

}
