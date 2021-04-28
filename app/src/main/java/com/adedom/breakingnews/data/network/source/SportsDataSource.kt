package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.entities.SportsEntity
import kotlinx.coroutines.flow.Flow

interface SportsDataSource {

    suspend fun saveSports(sportsEntity: SportsEntity)

    suspend fun getSportsList(): List<SportsEntity>

    fun getSportsFlow(): Flow<List<SportsEntity>>

    suspend fun deleteSports()

}
