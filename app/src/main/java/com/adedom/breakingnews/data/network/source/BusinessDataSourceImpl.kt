package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.db.entities.BusinessEntity
import kotlinx.coroutines.flow.Flow

class BusinessDataSourceImpl(
    private val appDatabase: AppDatabase,
) : BusinessDataSource {

    override suspend fun saveBusiness(businessEntity: BusinessEntity) {
        return appDatabase.getBusinessDao().saveBusiness(businessEntity)
    }

    override suspend fun getBusinessList(): List<BusinessEntity> {
        return appDatabase.getBusinessDao().getBusinessList()
    }

    override fun getBusinessFlow(): Flow<List<BusinessEntity>> {
        return appDatabase.getBusinessDao().getBusinessFlow()
    }

    override suspend fun deleteBusiness() {
        return appDatabase.getBusinessDao().deleteBusiness()
    }

}
