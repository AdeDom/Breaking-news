package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.db.entities.ScienceEntity
import kotlinx.coroutines.flow.Flow

class ScienceDataSourceImpl(
    private val appDatabase: AppDatabase,
) : ScienceDataSource {

    override suspend fun saveScience(scienceEntity: ScienceEntity) {
        return appDatabase.getScienceDao().saveScience(scienceEntity)
    }

    override suspend fun getScienceList(): List<ScienceEntity> {
        return appDatabase.getScienceDao().getScienceList()
    }

    override fun getScienceFlow(): Flow<List<ScienceEntity>> {
        return appDatabase.getScienceDao().getScienceFlow()
    }

    override suspend fun deleteScience() {
        return appDatabase.getScienceDao().deleteScience()
    }

}
