package com.meslmawy.ibtkarchallenge.di

import com.meslmawy.ibtkarchallenge.presentation.details.DetailsViewModel
import com.meslmawy.ibtkarchallenge.presentation.main.HomeViewModel
import com.meslmawy.ibtkarchallenge.presentation.photo.PhotoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        DetailsViewModel(get())
    }
    viewModel {
        PhotoViewModel(get())
    }
}