package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import kotlinx.coroutines.flow.Flow

interface GeneralDataSource {

    suspend fun saveGeneral(generalEntity: GeneralEntity)

    suspend fun getGeneralList(): List<GeneralEntity>

    fun getGeneralFlow(): Flow<List<GeneralEntity>>

    suspend fun deleteGeneral()

    suspend fun callBreakingNews(
        category: String,
        country: String? = null,
        page: Int? = null,
    ): BreakingNewsResponse

}
