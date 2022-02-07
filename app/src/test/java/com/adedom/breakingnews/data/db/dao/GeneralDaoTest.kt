package com.adedom.breakingnews.data.db.dao

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class GeneralDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: GeneralDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getGeneralDao()
    }

    @After
    fun cleanUp() {
        db.close()
        stopKoin()
    }

    @Test
    fun writeAndReadDatabase() = runBlocking {
        val generalEntity1 = GeneralEntity(
            2,
            listOf(
                ArticleDb(
                    id = 123456,
                    author = "Thairath",
                    title = "\"ศิริราช\" แจง บุคลากร 3 ราย ชาแขน ขา ใบหน้าด้านซ้าย หลังฉีดวัคซีนโควิด - ไทยรัฐ",
                    description = "รพ.ศิริราช ชี้แจงกรณีบุคลากรของคณะแพทยศาสตร์ 3 ราย ฉีดวัคซีนโควิด-19 แล้วเกิดผลข้างเคียง มีอาการชาบริเวณแขน ขา ใบหน้าด้านซ้าย",
                    urlToImage = "https://static.thairath.co.th/media/dFQROr7oWzulq5Fa4VuAeHo1zLbnAcXrvxHLvH9G5Rz8Heao69vWyGq85D5arN2qykr.jpg",
                    publishedAt = "2021-04-23T13:17:12Z",
                ),
                ArticleDb(
                    id = 234567,
                    author = "Google News",
                    title = "ไฟไหม้ รพ.อินเดีย ซ้ำเติมวิกฤตโควิด เสียชีวิต 13 คน - Thai PBS News",
                    description = null,
                    urlToImage = null,
                    publishedAt = "2021-04-23T12:59:08Z",
                )
            )
        )

        dao.saveGeneral(generalEntity1)
        val result = dao.getGeneralList()

        assertThat(result).isEqualTo(listOf(generalEntity1))
    }

    @Test
    fun deleteGeneral_returnIsEmpty() = runBlocking {
        val generalEntity1 = GeneralEntity(
            2,
            listOf(
                ArticleDb(
                    id = 123456,
                    author = "Thairath",
                    title = "\"ศิริราช\" แจง บุคลากร 3 ราย ชาแขน ขา ใบหน้าด้านซ้าย หลังฉีดวัคซีนโควิด - ไทยรัฐ",
                    description = "รพ.ศิริราช ชี้แจงกรณีบุคลากรของคณะแพทยศาสตร์ 3 ราย ฉีดวัคซีนโควิด-19 แล้วเกิดผลข้างเคียง มีอาการชาบริเวณแขน ขา ใบหน้าด้านซ้าย",
                    urlToImage = "https://static.thairath.co.th/media/dFQROr7oWzulq5Fa4VuAeHo1zLbnAcXrvxHLvH9G5Rz8Heao69vWyGq85D5arN2qykr.jpg",
                    publishedAt = "2021-04-23T13:17:12Z",
                ),
                ArticleDb(
                    id = 234567,
                    author = "Google News",
                    title = "ไฟไหม้ รพ.อินเดีย ซ้ำเติมวิกฤตโควิด เสียชีวิต 13 คน - Thai PBS News",
                    description = null,
                    urlToImage = null,
                    publishedAt = "2021-04-23T12:59:08Z",
                )
            )
        )
        dao.saveGeneral(generalEntity1)

        dao.deleteGeneral()
        val result = dao.getGeneralList()

        assertThat(result).isEmpty()
    }

}
