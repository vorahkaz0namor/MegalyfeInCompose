package com.example.megalifecompose.di

import android.app.Application
import android.content.Context
import com.example.megalifecompose.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun application(app: App): Application = app

}