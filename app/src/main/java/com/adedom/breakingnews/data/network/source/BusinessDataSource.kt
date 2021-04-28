package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.entities.BusinessEntity
import kotlinx.coroutines.flow.Flow

interface BusinessDataSource {

    suspend fun saveBusiness(businessEntity: BusinessEntity)

    suspend fun getBusinessList(): List<BusinessEntity>

    fun getBusinessFlow(): Flow<List<BusinessEntity>>

    suspend fun deleteBusiness()

}
