package com.adedom.breakingnews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adedom.breakingnews.data.db.entities.ScienceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScienceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveScience(scienceEntity: ScienceEntity)

    @Query("SELECT * FROM science")
    suspend fun getScienceList(): List<ScienceEntity>

    @Query("SELECT * FROM science")
    fun getScienceFlow(): Flow<List<ScienceEntity>>

    @Query("DELETE FROM science")
    suspend fun deleteScience()

}
