package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.db.entities.SportsEntity
import kotlinx.coroutines.flow.Flow

class SportsDataSourceImpl(
    private val appDatabase: AppDatabase,
) : SportsDataSource {

    override suspend fun saveSports(sportsEntity: SportsEntity) {
        return appDatabase.getSportsDao().saveSports(sportsEntity)
    }

    override suspend fun getSportsList(): List<SportsEntity> {
        return appDatabase.getSportsDao().getSportsList()
    }

    override fun getSportsFlow(): Flow<List<SportsEntity>> {
        return appDatabase.getSportsDao().getSportsFlow()
    }

    override suspend fun deleteSports() {
        return appDatabase.getSportsDao().deleteSports()
    }

}
