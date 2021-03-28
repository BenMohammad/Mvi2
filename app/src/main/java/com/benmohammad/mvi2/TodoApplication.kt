package com.benmohammad.mvi2

import android.app.Application
import com.benmohammad.mvi2.di.AppComponent
import com.benmohammad.mvi2.di.AppModule
import com.benmohammad.mvi2.di.DaggerAppComponent
import timber.log.Timber

class TodoApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }


    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}