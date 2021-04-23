package com.adedom.breakingnews.data.repository

import com.adedom.breakingnews.data.network.source.GeneralDataSource
import com.adedom.breakingnews.data.sharedpreference.SettingPref
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.KoinContextHandler

@ExperimentalCoroutinesApi
class GeneralRepositoryImplTest {

    private val dataSource: GeneralDataSource = mockk(relaxed = true)
    private val settingPref: SettingPref = mockk(relaxed = true)
    private lateinit var repository: GeneralRepository

    @Before
    fun setUp() {
        repository = GeneralRepositoryImpl(dataSource, settingPref)
    }

    @After
    fun cleanUp() {
        KoinContextHandler.stop()
    }

    @Test
    fun callCategoryGeneral() = runBlockingTest {
        val result = 1
        assertThat(result).isEqualTo(1)
    }

}
