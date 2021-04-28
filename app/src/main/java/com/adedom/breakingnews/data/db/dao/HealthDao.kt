package com.adedom.breakingnews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adedom.breakingnews.data.db.entities.HealthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHealth(healthEntity: HealthEntity)

    @Query("SELECT * FROM health")
    suspend fun getHealthList(): List<HealthEntity>

    @Query("SELECT * FROM health")
    fun getHealthFlow(): Flow<List<HealthEntity>>

    @Query("DELETE FROM health")
    suspend fun deleteHealth()

}
