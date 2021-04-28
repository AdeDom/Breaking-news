package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.entities.HealthEntity
import kotlinx.coroutines.flow.Flow

interface HealthDataSource {

    suspend fun saveHealth(healthEntity: HealthEntity)

    suspend fun getHealthList(): List<HealthEntity>

    fun getHealthFlow(): Flow<List<HealthEntity>>

    suspend fun deleteHealth()

}
