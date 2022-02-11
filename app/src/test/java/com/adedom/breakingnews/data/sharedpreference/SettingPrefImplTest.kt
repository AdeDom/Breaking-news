package com.adedom.breakingnews.data.sharedpreference

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SettingPrefImplTest {

    private lateinit var settingPref: SettingPref

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val sharedPreferences = context.getSharedPreferences(
            SettingPrefImpl.SETTING_PREF,
            Context.MODE_PRIVATE
        )
        settingPref = SettingPrefImpl(sharedPreferences)
    }

    @After
    fun cleanUp() {
        stopKoin()
    }

    @Test
    fun isSearchOnlyThaiNews_setTrue_returnTrue() {
        val isSearchOnlyThaiNews = true

        settingPref.isSearchOnlyThaiNews = isSearchOnlyThaiNews
        val result = settingPref.isSearchOnlyThaiNews

        assertThat(result).isTrue()
    }

    @Test
    fun isSearchOnlyThaiNews_setFalse_returnFalse() {
        val isSearchOnlyThaiNews = false

        settingPref.isSearchOnlyThaiNews = isSearchOnlyThaiNews
        val result = settingPref.isSearchOnlyThaiNews

        assertThat(result).isFalse()
    }

    @Test
    fun isSearchOnlyThaiNews_defaultValue_returnFalse(){
        val result = settingPref.isSearchOnlyThaiNews

        assertThat(result).isFalse()
    }

}
