package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.KoinContextHandler

class GeneralDataSourceImplTest {

    private val appDatabase: AppDatabase = mockk(relaxed = true)
    private lateinit var dataSource: GeneralDataSource

    @Before
    fun setUp() {
        dataSource = GeneralDataSourceImpl(appDatabase)
    }

    @After
    fun cleanUp() {
        KoinContextHandler.stop()
    }

    @Test
    fun getGeneralList() = runBlocking {
        val list = listOf(
            GeneralEntity(
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
        )
        coEvery { appDatabase.getGeneralDao().getGeneralList() } returns list

        val result = dataSource.getGeneralList()

        assertThat(result).isEqualTo(list)
    }

}
