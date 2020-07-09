package com.meslmawy.ibtkarchallenge.presentation

import android.app.Application
import android.os.Environment
import com.meslmawy.ibtkarchallenge.di.networkModule
import com.meslmawy.ibtkarchallenge.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.io.File

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ChallengApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(networkModule,viewModelModule)
        }
    }
}