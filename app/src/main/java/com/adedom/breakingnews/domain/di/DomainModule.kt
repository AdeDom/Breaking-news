package com.adedom.breakingnews.domain.di

import com.adedom.breakingnews.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {

    single<GetBusinessUseCase> { GetBusinessUseCaseImpl(get(), get()) }
    single<GetEntertainmentUseCase> { GetEntertainmentUseCaseImpl(get(), get()) }
    single<GetGeneralUseCase> { GetGeneralUseCaseImpl(get(), get()) }
    single<GetHealthUseCase> { GetHealthUseCaseImpl(get(), get()) }
    single<GetScienceUseCase> { GetScienceUseCaseImpl(get(), get()) }
    single<GetSportsUseCase> { GetSportsUseCaseImpl(get(), get()) }
    single<GetTechnologyUseCase> { GetTechnologyUseCaseImpl(get(), get()) }

}
