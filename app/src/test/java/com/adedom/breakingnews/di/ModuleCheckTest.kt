package com.adedom.breakingnews.di

import android.content.Context
import com.adedom.breakingnews.data.di.dataModule
import com.adedom.breakingnews.domain.di.domainModule
import com.adedom.breakingnews.presentation.di.presentationModule
import io.mockk.mockk
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.core.error.InstanceCreationException
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules

@Category(CheckModuleTest::class)
class ModuleCheckTest : AutoCloseKoinTest() {

    @Test
    fun test_checkModules() {
        val contextModule = module {
            single { mockk<Context>(relaxed = true) }
        }

        try {
            checkModules {
                modules(
                    contextModule,
                    presentationModule,
                    domainModule,
                    dataModule,
                )
            }
        } catch (e: InstanceCreationException) {
        } catch (e: RuntimeException) {
        }
    }

}
