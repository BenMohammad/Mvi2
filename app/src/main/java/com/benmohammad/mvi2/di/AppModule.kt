package com.benmohammad.mvi2.di

import android.content.Context
import androidx.transition.Visibility
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val context: Context) {
    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }
}