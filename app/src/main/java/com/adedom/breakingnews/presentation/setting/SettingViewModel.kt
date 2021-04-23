package com.adedom.breakingnews.presentation.setting

import com.adedom.breakingnews.base.BaseViewModel
import com.adedom.breakingnews.data.sharedpreference.SettingPref

class SettingViewModel(
    private val settingPref: SettingPref,
) : BaseViewModel<Unit>(Unit) {

    fun setIsSearchOnlyThaiNews(isSearchOnlyThaiNews: Boolean) {
        settingPref.isSearchOnlyThaiNews = isSearchOnlyThaiNews
    }

    fun getIsSearchOnlyThaiNews(): Boolean {
        return settingPref.isSearchOnlyThaiNews
    }

}
