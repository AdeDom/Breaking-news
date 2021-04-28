package com.adedom.breakingnews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adedom.breakingnews.data.db.entities.TechnologyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TechnologyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTechnology(technologyEntity: TechnologyEntity)

    @Query("SELECT * FROM technology")
    suspend fun getTechnologyList(): List<TechnologyEntity>

    @Query("SELECT * FROM technology")
    fun getTechnologyFlow(): Flow<List<TechnologyEntity>>

    @Query("DELETE FROM technology")
    suspend fun deleteTechnology()

}
