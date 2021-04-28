package com.adedom.breakingnews.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adedom.breakingnews.data.db.converter.ArticleConverter
import com.adedom.breakingnews.data.db.dao.*
import com.adedom.breakingnews.data.db.entities.*

@Database(
    entities = [BusinessEntity::class, EntertainmentEntity::class, GeneralEntity::class, HealthEntity::class,
        ScienceEntity::class, SportsEntity::class, TechnologyEntity::class],
    version = 2
)
@TypeConverters(ArticleConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getBusinessDao(): BusinessDao

    abstract fun getEntertainmentDao(): EntertainmentDao

    abstract fun getGeneralDao(): GeneralDao

    abstract fun getHealthDao(): HealthDao

    abstract fun getScienceDao(): ScienceDao

    abstract fun getSportsDao(): SportsDao

    abstract fun getTechnologyDao(): TechnologyDao

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
