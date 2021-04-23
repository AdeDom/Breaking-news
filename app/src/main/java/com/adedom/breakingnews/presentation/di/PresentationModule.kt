package com.adedom.breakingnews.presentation.di

import com.adedom.breakingnews.presentation.general.GeneralViewModel
import com.adedom.breakingnews.presentation.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { GeneralViewModel(get()) }
    viewModel { SettingViewModel(get()) }

}
