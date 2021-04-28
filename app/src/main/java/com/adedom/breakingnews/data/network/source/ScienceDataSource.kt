package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.entities.ScienceEntity
import kotlinx.coroutines.flow.Flow

interface ScienceDataSource {

    suspend fun saveScience(scienceEntity: ScienceEntity)

    suspend fun getScienceList(): List<ScienceEntity>

    fun getScienceFlow(): Flow<List<ScienceEntity>>

    suspend fun deleteScience()

}
