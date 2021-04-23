package com.adedom.breakingnews.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SettingPrefImpl(
    context: Context,
) : SettingPref {

    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences(SETTING_PREF, Context.MODE_PRIVATE)

    override var isSearchOnlyThaiNews: Boolean
        get() = sharedPreference.getBoolean(IS_SEARCH_ONLY_THAI_NEWS, false)
        set(value) {
            sharedPreference.edit {
                putBoolean(IS_SEARCH_ONLY_THAI_NEWS, value)
            }
        }

    companion object {
        private const val SETTING_PREF = "setting"
        private const val IS_SEARCH_ONLY_THAI_NEWS = "isSearchOnlyThaiNews"
    }

}
