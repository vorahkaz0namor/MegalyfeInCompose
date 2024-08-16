package com.ccink.model.di

import com.ccink.model.dao.StoreDao
import com.ccink.model.database.AppDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {
    @Singleton
    @Provides
    fun provideStoreDao(appDb: AppDb): StoreDao = appDb.storeDao()
}