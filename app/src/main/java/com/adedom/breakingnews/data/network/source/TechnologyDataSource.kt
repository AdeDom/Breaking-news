package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.entities.TechnologyEntity
import kotlinx.coroutines.flow.Flow

interface TechnologyDataSource {

    suspend fun saveTechnology(technologyEntity: TechnologyEntity)

    suspend fun getTechnologyList(): List<TechnologyEntity>

    fun getTechnologyFlow(): Flow<List<TechnologyEntity>>

    suspend fun deleteTechnology()

}
