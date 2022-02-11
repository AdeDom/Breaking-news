package com.adedom.breakingnews.data.sharedpreference

import android.content.SharedPreferences
import androidx.core.content.edit

class SettingPrefImpl(
    private val sharedPreferences: SharedPreferences
) : SettingPref {

    override var isSearchOnlyThaiNews: Boolean
        get() = sharedPreferences.getBoolean(IS_SEARCH_ONLY_THAI_NEWS, false)
        set(value) {
            sharedPreferences.edit {
                putBoolean(IS_SEARCH_ONLY_THAI_NEWS, value)
            }
        }

    companion object {
        const val SETTING_PREF = "setting"
        private const val IS_SEARCH_ONLY_THAI_NEWS = "isSearchOnlyThaiNews"
    }

}
