package com.adedom.breakingnews.presentation.di

import com.adedom.breakingnews.presentation.business.BusinessViewModel
import com.adedom.breakingnews.presentation.entertainment.EntertainmentViewModel
import com.adedom.breakingnews.presentation.general.GeneralViewModel
import com.adedom.breakingnews.presentation.health.HealthViewModel
import com.adedom.breakingnews.presentation.science.ScienceViewModel
import com.adedom.breakingnews.presentation.setting.SettingViewModel
import com.adedom.breakingnews.presentation.sports.SportsViewModel
import com.adedom.breakingnews.presentation.technology.TechnologyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { BusinessViewModel(get()) }
    viewModel { EntertainmentViewModel(get()) }
    viewModel { GeneralViewModel(get()) }
    viewModel { HealthViewModel(get()) }
    viewModel { ScienceViewModel(get()) }
    viewModel { SportsViewModel(get()) }
    viewModel { TechnologyViewModel(get()) }
    viewModel { SettingViewModel(get()) }

}
