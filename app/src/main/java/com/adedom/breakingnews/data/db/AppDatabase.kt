package com.adedom.breakingnews.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adedom.breakingnews.data.db.converter.GeneralConverter
import com.adedom.breakingnews.data.db.dao.GeneralDao
import com.adedom.breakingnews.data.db.entities.GeneralEntity

@Database(
    entities = [GeneralEntity::class],
    version = 1
)
@TypeConverters(GeneralConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getGeneralDao(): GeneralDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "breaking_news.db"
        ).fallbackToDestructiveMigration().build()
    }

}
