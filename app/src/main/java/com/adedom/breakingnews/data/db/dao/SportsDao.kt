package com.adedom.breakingnews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adedom.breakingnews.data.db.entities.SportsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SportsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSports(sportsEntity: SportsEntity)

    @Query("SELECT * FROM sports")
    suspend fun getSportsList(): List<SportsEntity>

    @Query("SELECT * FROM sports")
    fun getSportsFlow(): Flow<List<SportsEntity>>

    @Query("DELETE FROM sports")
    suspend fun deleteSports()

}
