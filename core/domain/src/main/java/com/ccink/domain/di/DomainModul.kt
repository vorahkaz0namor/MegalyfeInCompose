package com.ccink.domain.di

import com.ccink.domain.useCases.LoginUseCase
import com.ccink.domain.useCases.LoginUseCaseImpl
import com.ccink.domain.useCases.MainUseCase
import com.ccink.domain.useCases.MainUseCaseImpl
import com.ccink.domain.useCases.StoreUseCase
import com.ccink.domain.useCases.StoreUseCaseImpl
import com.ccink.model.di.DataModule
import com.ccink.model.repository.StoreRepository
import com.ccink.model.repository.LoginRepository
import com.ccink.model.repository.MainRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [DataModule::class, DispatcherModule::class])
class DomainModule {

    @Singleton
    @Provides
    fun provideLoginUseCase(repository: LoginRepository, @IoDispatcher dispatcher: CoroutineDispatcher): LoginUseCase {
        return LoginUseCaseImpl(repository, dispatcher)
    }

    @Singleton
    @Provides
    fun provideStoreUseCase(
        repository: StoreRepository,
        @IoDispatcher
        dispatcher: CoroutineDispatcher
    ): StoreUseCase = StoreUseCaseImpl(repository, dispatcher)

    @Singleton
    @Provides
    fun provideMainUseCase(
        mainRepository: MainRepository,
        storeRepository: StoreRepository,
        @IoDispatcher
        dispatcher: CoroutineDispatcher
    ): MainUseCase = MainUseCaseImpl(mainRepository, storeRepository, dispatcher)
}

@Module
object DispatcherModule {

    @Singleton
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Singleton
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher