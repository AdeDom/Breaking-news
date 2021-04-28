package com.adedom.breakingnews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adedom.breakingnews.data.db.entities.EntertainmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntertainmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEntertainment(entertainmentEntity: EntertainmentEntity)

    @Query("SELECT * FROM entertainment")
    suspend fun getEntertainmentList(): List<EntertainmentEntity>

    @Query("SELECT * FROM entertainment")
    fun getEntertainmentFlow(): Flow<List<EntertainmentEntity>>

    @Query("DELETE FROM entertainment")
    suspend fun deleteEntertainment()

}
