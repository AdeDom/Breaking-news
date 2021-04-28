package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.db.entities.TechnologyEntity
import kotlinx.coroutines.flow.Flow

class TechnologyDataSourceImpl(
    private val appDatabase: AppDatabase,
) : TechnologyDataSource {

    override suspend fun saveTechnology(technologyEntity: TechnologyEntity) {
        return appDatabase.getTechnologyDao().saveTechnology(technologyEntity)
    }

    override suspend fun getTechnologyList(): List<TechnologyEntity> {
        return appDatabase.getTechnologyDao().getTechnologyList()
    }

    override fun getTechnologyFlow(): Flow<List<TechnologyEntity>> {
        return appDatabase.getTechnologyDao().getTechnologyFlow()
    }

    override suspend fun deleteTechnology() {
        return appDatabase.getTechnologyDao().deleteTechnology()
    }

}
