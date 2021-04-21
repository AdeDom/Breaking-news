package com.adedom.breakingnews.domain.di

import com.adedom.breakingnews.domain.usecase.GetGeneralUseCase
import com.adedom.breakingnews.domain.usecase.GetGeneralUseCaseImpl
import org.koin.dsl.module

val domainModule = module {

    single<GetGeneralUseCase> { GetGeneralUseCaseImpl(get(), get()) }

}
