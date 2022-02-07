package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.ArticleData
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.model.response.SourceData
import com.adedom.breakingnews.data.network.source.BreakingNewsDataSource
import com.adedom.breakingnews.data.network.source.GeneralDataSource
import com.adedom.breakingnews.data.sharedpreference.SettingPref
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin

class GeneralRepositoryImplTest {

    private val generalDataSource: GeneralDataSource = mockk(relaxed = true)
    private val breakingNewsDataSource: BreakingNewsDataSource = mockk(relaxed = true)
    private val settingPref: SettingPref = mockk(relaxed = true)
    private lateinit var repository: GeneralRepository

    @Before
    fun setUp() {
        repository = GeneralRepositoryImpl(generalDataSource, breakingNewsDataSource, settingPref)
    }

    @After
    fun cleanUp() {
        stopKoin()
    }

    @Test
    fun callCategoryGeneral_success() = runBlocking {
        val response = responseSuccess()
        val entityList = listOf(
            GeneralEntity(
                1,
                listOf(
                    ArticleDb(
                        id = 1,
                        author = "Thairath",
                        title = "\"ศิริราช\" แจง บุคลากร 3 ราย ชาแขน ขา ใบหน้าด้านซ้าย หลังฉีดวัคซีนโควิด - ไทยรัฐ",
                        description = "รพ.ศิริราช ชี้แจงกรณีบุคลากรของคณะแพทยศาสตร์ 3 ราย ฉีดวัคซีนโควิด-19 แล้วเกิดผลข้างเคียง มีอาการชาบริเวณแขน ขา ใบหน้าด้านซ้าย",
                        url = "https://www.thairath.co.th/news/society/2075966",
                        urlToImage = "https://static.thairath.co.th/media/dFQROr7oWzulq5Fa4VuAeHo1zLbnAcXrvxHLvH9G5Rz8Heao69vWyGq85D5arN2qykr.jpg",
                        publishedAt = "2021-04-23T13:17:12Z",
                    ),
                )
            )
        )
        coEvery { generalDataSource.getGeneralList() } returns entityList
        coEvery { breakingNewsDataSource.callBreakingNews(any(), any()) } returns response

        val resource = repository.callCategoryGeneral()

        assertThat(resource).isEqualTo(Resource.Success(response))
    }

    @Test
    fun callCategoryGeneral_error() = runBlocking {
        val throwable = Throwable("Api error")
        coEvery { breakingNewsDataSource.callBreakingNews(any(), any()) } throws throwable

        val resource = repository.callCategoryGeneral()

        assertThat(resource).isEqualTo(Resource.Error(throwable))
    }

    @Test
    fun callCategoryGeneralNextPage_success() = runBlocking {
        val response = responseSuccess()
        coEvery { breakingNewsDataSource.callBreakingNews(any(), any(), page = any()) } returns response

        val resource = repository.callCategoryGeneralNextPage(1)

        assertThat(resource).isEqualTo(Resource.Success(response))
    }

    @Test
    fun callCategoryGeneralNextPage_error() = runBlocking {
        val throwable = Throwable("Api error")
        coEvery { breakingNewsDataSource.callBreakingNews(any(), any(), page = any()) } throws throwable

        val resource = repository.callCategoryGeneralNextPage(1)

        assertThat(resource).isEqualTo(Resource.Error(throwable))
    }

    @Test
    fun callCategoryGeneralSearch_success() = runBlocking {
        val response = responseSuccess()
        coEvery { breakingNewsDataSource.callBreakingNews(any(), any(), query = any()) } returns response

        val resource = repository.callCategoryGeneralSearch("Covid")

        assertThat(resource).isEqualTo(Resource.Success(response))
    }

    @Test
    fun callCategoryGeneralSearch_error() = runBlocking {
        val throwable = Throwable("Api error")
        coEvery { breakingNewsDataSource.callBreakingNews(any(), any(), query = any()) } throws throwable

        val resource = repository.callCategoryGeneralSearch("Covid")

        assertThat(resource).isEqualTo(Resource.Error(throwable))
    }

    private fun responseSuccess() = BreakingNewsResponse(
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

}
