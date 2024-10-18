package di

import data.detail.repository.DetailRepositoryImp
import domain.detail.DetailRepository
import domain.usecase.GetDrinkFromIdUseCase
import domain.usecase.GetDrinksFromSearchUseCase
import domain.usecase.GetHomeScreenUseCase
import domain.usecase.GetNonAlchoholicDrinksUseCase
import io.ktor.client.HttpClient
import networking.DrinksService
import org.koin.dsl.module

val appModule = module {
    single { GetHomeScreenUseCase(get()) }
    single { DrinksService(get()) }
    single { GetNonAlchoholicDrinksUseCase(get()) }
    single { GetDrinksFromSearchUseCase(get()) }
    single { GetDrinkFromIdUseCase(get()) }
    single<DetailRepository> { DetailRepositoryImp(get()) }

    single { client }
}

expect val client: HttpClient