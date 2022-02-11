package com.adedom.breakingnews.data.di

import android.content.Context
import android.content.SharedPreferences
import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.network.source.*
import com.adedom.breakingnews.data.repository.*
import com.adedom.breakingnews.data.sharedpreference.SettingPref
import com.adedom.breakingnews.data.sharedpreference.SettingPrefImpl
import org.koin.dsl.module

val dataModule = module {

    single { AppDatabase(get()) }
    single { DataSourceProvider() }

    single<BreakingNewsDataSource> { BreakingNewsDataSourceImpl(get()) }

    single<BusinessDataSource> { BusinessDataSourceImpl(get()) }
    single<EntertainmentDataSource> { EntertainmentDataSourceImpl(get()) }
    single<GeneralDataSource> { GeneralDataSourceImpl(get()) }
    single<HealthDataSource> { HealthDataSourceImpl(get()) }
    single<ScienceDataSource> { ScienceDataSourceImpl(get()) }
    single<SportsDataSource> { SportsDataSourceImpl(get()) }
    single<TechnologyDataSource> { TechnologyDataSourceImpl(get()) }

    single<BusinessRepository> { BusinessRepositoryImpl(get(), get(), get()) }
    single<EntertainmentRepository> { EntertainmentRepositoryImpl(get(), get(), get()) }
    single<GeneralRepository> { GeneralRepositoryImpl(get(), get(), get()) }
    single<HealthRepository> { HealthRepositoryImpl(get(), get(), get()) }
    single<ScienceRepository> { ScienceRepositoryImpl(get(), get(), get()) }
    single<SportsRepository> { SportsRepositoryImpl(get(), get(), get()) }
    single<TechnologyRepository> { TechnologyRepositoryImpl(get(), get(), get()) }

    single<SharedPreferences> {
        get<Context>().getSharedPreferences(SettingPrefImpl.SETTING_PREF, Context.MODE_PRIVATE)
    }
    single<SettingPref> { SettingPrefImpl(get()) }

}
