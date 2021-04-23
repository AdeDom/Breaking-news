package com.adedom.breakingnews.data.di

import com.adedom.breakingnews.data.db.AppDatabase
import com.adedom.breakingnews.data.network.source.DataSourceProvider
import com.adedom.breakingnews.data.network.source.GeneralDataSource
import com.adedom.breakingnews.data.network.source.GeneralDataSourceImpl
import com.adedom.breakingnews.data.repository.GeneralRepository
import com.adedom.breakingnews.data.repository.GeneralRepositoryImpl
import com.adedom.breakingnews.data.sharedpreference.SettingPref
import com.adedom.breakingnews.data.sharedpreference.SettingPrefImpl
import org.koin.dsl.module

val dataModule = module {

    single { AppDatabase(get()) }
    single { DataSourceProvider() }
    single<GeneralDataSource> { GeneralDataSourceImpl(get(), get()) }
    single<GeneralRepository> { GeneralRepositoryImpl(get()) }

    single<SettingPref> { SettingPrefImpl(get()) }

}
