package com.benmohammad.mvi2

import android.app.Application
import android.content.Context
import com.benmohammad.mvi2.di.AppComponent
import com.benmohammad.mvi2.di.AppModule
import com.benmohammad.mvi2.di.DaggerAppComponent
import timber.log.Timber

class TodoApplication: Application() {

    val appComponent by lazy {
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

fun Context.appComponent(): AppComponent {
    return (applicationContext as TodoApplication).appComponent
}