package com.adedom.breakingnews.domain.usecase

import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.model.response.ArticleData
import com.adedom.breakingnews.data.model.response.BreakingNewsResponse
import com.adedom.breakingnews.data.model.response.SourceData
import com.adedom.breakingnews.data.network.source.GeneralDataSource
import com.adedom.breakingnews.data.repository.GeneralRepository
import com.adedom.breakingnews.data.repository.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.KoinContextHandler

class GetGeneralUseCaseImplTest {

    private val dataSource: GeneralDataSource = mockk(relaxed = true)
    private val repository: GeneralRepository = mockk(relaxed = true)
    private lateinit var useCase: GetGeneralUseCase

    @Before
    fun setUp() {
        useCase = GetGeneralUseCaseImpl(dataSource, repository)
    }

    @After
    fun cleanUp() {
        KoinContextHandler.stop()
    }

    @Test
    fun mapGeneralEntitySetDate() {
        val articleDb1 = ArticleDb(
            id = 123456,
            author = "Thairath",
            title = "\"ศิริราช\" แจง บุคลากร 3 ราย ชาแขน ขา ใบหน้าด้านซ้าย หลังฉีดวัคซีนโควิด - ไทยรัฐ",
            description = "รพ.ศิริราช ชี้แจงกรณีบุคลากรของคณะแพทยศาสตร์ 3 ราย ฉีดวัคซีนโควิด-19 แล้วเกิดผลข้างเคียง มีอาการชาบริเวณแขน ขา ใบหน้าด้านซ้าย",
            urlToImage = "https://static.thairath.co.th/media/dFQROr7oWzulq5Fa4VuAeHo1zLbnAcXrvxHLvH9G5Rz8Heao69vWyGq85D5arN2qykr.jpg",
            publishedAt = "2021-04-23T13:17:12Z",
        )
        val articleDb2 = ArticleDb(
            id = 234567,
            author = "Google News",
            title = "ไฟไหม้ รพ.อินเดีย ซ้ำเติมวิกฤตโควิด เสียชีวิต 13 คน - Thai PBS News",
            description = null,
            urlToImage = null,
            publishedAt = "2021-04-23T12:59:08Z",
        )
        val generalEntityList = listOf(
            GeneralEntity(2, listOf(articleDb1, articleDb2)),
            GeneralEntity(2, listOf(articleDb1, articleDb2))
        )

        val result = useCase.mapGeneralEntitySetDate(generalEntityList)

        val articleDb3 = ArticleDb(
            id = 123456,
            author = "Thairath",
            title = "\"ศิริราช\" แจง บุคลากร 3 ราย ชาแขน ขา ใบหน้าด้านซ้าย หลังฉีดวัคซีนโควิด - ไทยรัฐ",
            description = "รพ.ศิริราช ชี้แจงกรณีบุคลากรของคณะแพทยศาสตร์ 3 ราย ฉีดวัคซีนโควิด-19 แล้วเกิดผลข้างเคียง มีอาการชาบริเวณแขน ขา ใบหน้าด้านซ้าย",
            urlToImage = "https://static.thairath.co.th/media/dFQROr7oWzulq5Fa4VuAeHo1zLbnAcXrvxHLvH9G5Rz8Heao69vWyGq85D5arN2qykr.jpg",
            publishedAt = "23/4/2021 13:17",
        )
        val articleDb4 = ArticleDb(
            id = 234567,
            author = "Google News",
            title = "ไฟไหม้ รพ.อินเดีย ซ้ำเติมวิกฤตโควิด เสียชีวิต 13 คน - Thai PBS News",
            description = null,
            urlToImage = null,
            publishedAt = "23/4/2021 12:59",
        )
        val generalEntity = GeneralEntity(2, listOf(articleDb3, articleDb4))
        assertThat(result).isEqualTo(generalEntity)
    }

    @Test
    fun callCategoryGeneral_returnSuccess() = runBlocking {
        val resource = resourceSuccess()
        coEvery { repository.callCategoryGeneral() } returns resource

        val result = useCase.callCategoryGeneral()

        assertThat(result).isEqualTo(resource)
    }

    @Test
    fun callCategoryGeneral_returnError() = runBlocking {
        val resource = Resource.Error(Throwable("Api error"))
        coEvery { repository.callCategoryGeneral() } returns resource

        val result = useCase.callCategoryGeneral()

        assertThat(result).isEqualTo(resource)
    }

    @Test
    fun callCategoryGeneralNextPage_returnSuccess() = runBlocking {
        val resource = resourceSuccess()
        val generalEntityList = generalEntityList()
        coEvery { dataSource.getGeneralList() } returns generalEntityList
        coEvery { repository.callCategoryGeneralNextPage(any()) } returns resource

        val result = useCase.callCategoryGeneralNextPage()

        assertThat(result).isEqualTo(resource)
    }

    @Test
    fun callCategoryGeneralNextPage_returnError() = runBlocking {
        val resource = Resource.Error(Throwable("Api error"))
        val generalEntityList = generalEntityList()
        coEvery { dataSource.getGeneralList() } returns generalEntityList
        coEvery { repository.callCategoryGeneralNextPage(any()) } returns resource

        val result = useCase.callCategoryGeneralNextPage()

        assertThat(result).isEqualTo(resource)
    }

    @Test
    fun callCategoryGeneralSearch_returnSuccess() = runBlocking {
        val resource = resourceSuccess()
        coEvery { repository.callCategoryGeneralSearch(any()) } returns resource

        val result = useCase.callCategoryGeneralSearch("Covid")

        assertThat(result).isEqualTo(resource)
    }

    @Test
    fun callCategoryGeneralSearch_returnError() = runBlocking {
        val resource = Resource.Error(Throwable("Api error"))
        coEvery { repository.callCategoryGeneralSearch("Covid") } returns resource

        val result = useCase.callCategoryGeneralSearch("Covid")

        assertThat(result).isEqualTo(resource)
    }

    private fun generalEntityList(): List<GeneralEntity> {
        return listOf(
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
    }

    private fun resourceSuccess(): Resource.Success<BreakingNewsResponse> {
        return Resource.Success(
            BreakingNewsResponse(
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
        )
    }

}
