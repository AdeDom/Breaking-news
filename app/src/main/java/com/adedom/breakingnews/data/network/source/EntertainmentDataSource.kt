package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.entities.EntertainmentEntity
import kotlinx.coroutines.flow.Flow

interface EntertainmentDataSource {

    suspend fun saveEntertainment(entertainmentEntity: EntertainmentEntity)

    suspend fun getEntertainmentList(): List<EntertainmentEntity>

    fun getEntertainmentFlow(): Flow<List<EntertainmentEntity>>

    suspend fun deleteEntertainment()

}
