package com.adedom.breakingnews

import android.app.Application
import com.adedom.breakingnews.data.di.dataModule
import com.adedom.breakingnews.domain.di.domainModule
import com.adedom.breakingnews.presentation.di.presentationModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
//            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                    presentationModule,
                    domainModule,
                    dataModule,
                )
            )
        }

        Stetho.initializeWithDefaults(this)
    }
}
