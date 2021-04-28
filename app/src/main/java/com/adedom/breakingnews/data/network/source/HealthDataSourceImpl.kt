package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.db.entities.HealthEntity
import kotlinx.coroutines.flow.Flow

class HealthDataSourceImpl(
    private val appDatabase: AppDatabase,
) : HealthDataSource {

    override suspend fun saveHealth(healthEntity: HealthEntity) {
        return appDatabase.getHealthDao().saveHealth(healthEntity)
    }

    override suspend fun getHealthList(): List<HealthEntity> {
        return appDatabase.getHealthDao().getHealthList()
    }

    override fun getHealthFlow(): Flow<List<HealthEntity>> {
        return appDatabase.getHealthDao().getHealthFlow()
    }

    override suspend fun deleteHealth() {
        return appDatabase.getHealthDao().deleteHealth()
    }

}
