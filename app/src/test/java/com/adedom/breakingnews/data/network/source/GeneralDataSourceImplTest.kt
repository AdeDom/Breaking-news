package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.ArticleData
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.model.response.SourceData
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
    private val dataSourceProvider: DataSourceProvider = mockk(relaxed = true)
    private lateinit var dataSource: GeneralDataSource

    @Before
    fun setUp() {
        dataSource = GeneralDataSourceImpl(appDatabase, dataSourceProvider)
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

    @Test
    fun callBreakingNews() = runBlocking {
        val response = BreakingNewsResponse(
            "ok",
            38,
            listOf(
                ArticleData(
                    source = SourceData(null, "Thairath.co.th"),
                    author = "Thairath",
                    title = "\"ศิริราช\" แจง บุคลากร 3 ราย ชาแขน ขา ใบหน้าด้านซ้าย หลังฉีดวัคซีนโควิด - ไทยรัฐ",
                    description = "รพ.ศิริราช ชี้แจงกรณีบุคลากรของคณะแพทยศาสตร์ 3 ราย ฉีดวัคซีนโควิด-19 แล้วเกิดผลข้างเคียง มีอาการชาบริเวณแขน ขา ใบหน้าด้านซ้าย",
                    url = "https://www.thairath.co.th/news/society/2075966",
                    urlToImage = "https://static.thairath.co.th/media/dFQROr7oWzulq5Fa4VuAeHo1zLbnAcXrvxHLvH9G5Rz8Heao69vWyGq85D5arN2qykr.jpg",
                    publishedAt = "2021-04-23T13:17:12Z",
                    content = "23 .. 2 1 20 - 30 –19 22 2564 (MRI)  \r\n.. (mass vaccination) (WHO) Immunization Stress - Related Response (ISRR) 2 \r\n1. 2. -19 \r\n24 - 48 \r\n-19 ."
                ),
                ArticleData(
                    source = SourceData("google-news", "Google News"),
                    author = "Google News",
                    title = "ไฟไหม้ รพ.อินเดีย ซ้ำเติมวิกฤตโควิด เสียชีวิต 13 คน - Thai PBS News",
                    description = null,
                    url = "https://news.google.com/__i/rss/rd/articles/CBMiK2h0dHBzOi8vd3d3LnlvdXR1YmUuY29tL3dhdGNoP3Y9b2RSc2xLbGNBQjjSAQA?oc=5",
                    urlToImage = null,
                    publishedAt = "2021-04-23T12:59:08Z",
                    content = null
                )
            )
        )
        coEvery {
            dataSourceProvider.getBreakingNews()
                .callBreakingNews("general", "th", "โควิด", 1)
        } returns response

        val result = dataSource.callBreakingNews("general", "th", "โควิด", 1)

        assertThat(result).isEqualTo(response)
    }

}
