package com.adedom.breakingnews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GeneralDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGeneral(generalEntity: GeneralEntity)

    @Query("SELECT * FROM general")
    suspend fun getGeneralList(): List<GeneralEntity>

    @Query("SELECT * FROM general")
    fun getGeneralFlow(): Flow<List<GeneralEntity>>

    @Query("DELETE FROM general")
    suspend fun deleteGeneral()

}
