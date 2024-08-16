package com.ccink.model.di

import android.content.Context
import androidx.room.Room
import com.ccink.model.database.AppDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Singleton
    @Provides
    fun provideDb(
        context: Context
    ) : AppDb = Room.databaseBuilder(
        context = context,
        klass = AppDb::class.java,
        name = "megalife.db"
    ).build()
}