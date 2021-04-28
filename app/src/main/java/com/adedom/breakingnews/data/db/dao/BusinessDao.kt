package com.adedom.breakingnews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adedom.breakingnews.data.db.entities.BusinessEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBusiness(businessEntity: BusinessEntity)

    @Query("SELECT * FROM business")
    suspend fun getBusinessList(): List<BusinessEntity>

    @Query("SELECT * FROM business")
    fun getBusinessFlow(): Flow<List<BusinessEntity>>

    @Query("DELETE FROM business")
    suspend fun deleteBusiness()

}
