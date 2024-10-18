package di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.result.ResultViewModel
import presentation.home.HomeViewModel
import presentation.detail.DetailViewModel

actual val viewModelModule = module {
    singleOf(::HomeViewModel)
    singleOf(::ResultViewModel)
    singleOf(::DetailViewModel)
}