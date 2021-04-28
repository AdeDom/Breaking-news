package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.db.entities.EntertainmentEntity
import kotlinx.coroutines.flow.Flow

class EntertainmentDataSourceImpl(
    private val appDatabase: AppDatabase,
) : EntertainmentDataSource {

    override suspend fun saveEntertainment(entertainmentEntity: EntertainmentEntity) {
        return appDatabase.getEntertainmentDao().saveEntertainment(entertainmentEntity)
    }

    override suspend fun getEntertainmentList(): List<EntertainmentEntity> {
        return appDatabase.getEntertainmentDao().getEntertainmentList()
    }

    override fun getEntertainmentFlow(): Flow<List<EntertainmentEntity>> {
        return appDatabase.getEntertainmentDao().getEntertainmentFlow()
    }

    override suspend fun deleteEntertainment() {
        return appDatabase.getEntertainmentDao().deleteEntertainment()
    }

}
