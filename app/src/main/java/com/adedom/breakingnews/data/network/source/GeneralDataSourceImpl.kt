package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import kotlinx.coroutines.flow.Flow

class GeneralDataSourceImpl(
    private val appDatabase: AppDatabase,
    private val dataSourceProvider: DataSourceProvider,
) : GeneralDataSource {

    override suspend fun saveGeneral(generalEntity: GeneralEntity) {
        return appDatabase.getGeneralDao().saveGeneral(generalEntity)
    }

    override suspend fun getGeneralList(): List<GeneralEntity> {
        return appDatabase.getGeneralDao().getGeneralList()
    }

    override fun getGeneralFlow(): Flow<List<GeneralEntity>> {
        return appDatabase.getGeneralDao().getGeneralFlow()
    }

    override suspend fun deleteGeneral() {
        return appDatabase.getGeneralDao().deleteGeneral()
    }

    override suspend fun callBreakingNews(
        category: String,
        country: String?,
        query: String?,
        page: Int?,
    ): BreakingNewsResponse {
        return dataSourceProvider.getBreakingNews().callBreakingNews(category, country, query, page)
    }

}
