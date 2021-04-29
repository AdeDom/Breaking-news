package com.adedom.breakingnews.presentation.general

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.domain.usecase.GetGeneralUseCase
import com.adedom.breakingnews.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.KoinContextHandler
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class GeneralViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getGeneralUseCase: GetGeneralUseCase = mockk(relaxed = true)
    private lateinit var viewModel: GeneralViewModel

    @Before
    fun setUp() {
        viewModel = GeneralViewModel(getGeneralUseCase)
    }

    @After
    fun cleanup() {
        KoinContextHandler.stop()
    }

    @Test
    fun callCategoryGeneral_returnError() {
        val throwable = Throwable("Api error")
        val resource = Resource.Error(throwable)
        coEvery { getGeneralUseCase.callCategoryGeneral() } returns resource

        viewModel.callCategoryGeneral()

        val error = viewModel.error.getOrAwaitValue()
        assertThat(error).isEqualTo(throwable)
    }

    @Test
    fun callCategoryGeneralNextPage_returnError() {
        val throwable = Throwable("Api error")
        val resource = Resource.Error(throwable)
        coEvery { getGeneralUseCase.callCategoryGeneralNextPage() } returns resource

        viewModel.callCategoryGeneralNextPage(24, 25)

        val error = viewModel.error.getOrAwaitValue()
        assertThat(error).isEqualTo(throwable)
    }

    @Test
    fun setStateSearch_searchCovid_returnCovid() {
        val messageSearch = "Covid"

        viewModel.setStateSearch(messageSearch)

        val search = viewModel.state.getOrAwaitValue().search
        assertThat(search).isEqualTo(messageSearch)
    }

    @Test
    fun callCategoryGeneralSearch_returnError() {
        val messageSearch = "Covid"
        val throwable = Throwable("Api error")
        val resource = Resource.Error(throwable)
        coEvery { getGeneralUseCase.callCategoryGeneralSearch(any()) } returns resource

        viewModel.setStateSearch(messageSearch)
        viewModel.callCategoryGeneralSearch()

        val search = viewModel.state.getOrAwaitValue().search
        val error = viewModel.error.getOrAwaitValue()
        assertThat(search).isEqualTo(messageSearch)
        assertThat(error).isEqualTo(throwable)
    }

}
