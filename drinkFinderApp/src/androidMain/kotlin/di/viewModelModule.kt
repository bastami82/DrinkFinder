package di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import presentation.result.ResultViewModel
import presentation.home.HomeViewModel
import presentation.detail.DetailViewModel

actual val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::ResultViewModel)
    viewModelOf(::DetailViewModel)
}