package com.ccink.model.di

import android.content.Context
import com.ccink.model.Constants.Companion.BACKEND_API_URL
import com.ccink.model.Constants.Companion.SERVER_API_URL
import com.ccink.model.config.ConfigService
import com.ccink.model.config.ConfigServiceImpl
import com.ccink.model.dao.StoreDao
import com.ccink.model.database.AppDb
import com.ccink.model.network.Api
import com.ccink.model.network.ApiService
import com.ccink.model.network.ApiServiceImpl
import com.ccink.model.network.StoreApi
import com.ccink.model.network.StoreApiService
import com.ccink.model.network.StoreApiServiceImpl
import com.ccink.model.repository.*
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [DbModule::class, DaoModule::class])
class DataModule {

    @Singleton
    @Provides
    fun providePrefsService(context: Context): ConfigService {
        return ConfigServiceImpl(context, "configurations")
    }

    @Singleton
    @Provides
    fun provideApiImpl(api: Api): ApiService {
        return ApiServiceImpl(api)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(configService: ConfigService): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor(configService))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .connectTimeout(60000, TimeUnit.MILLISECONDS)
            .readTimeout(60000, TimeUnit.MILLISECONDS)
            .callTimeout(60000, TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): Api {

        return Retrofit.Builder()
            .baseUrl(SERVER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(apiService: ApiService): LoginRepository {
        return LoginRepositoryImpl(apiService)
    }

    /**
     * Start of STORE section
     */
    @Singleton
    @Provides
    fun provideStoreApi(
        okHttpClient: OkHttpClient
    ): StoreApi = Retrofit.Builder()
        .baseUrl(BACKEND_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client((okHttpClient))
        .build()
        .create()

    @Singleton
    @Provides
    fun provideStoreApiServiceImpl(
        api: StoreApi
    ): StoreApiService = StoreApiServiceImpl(api)

    @Singleton
    @Provides
    fun provideStoreRepository(
        storeApiService: StoreApiService,
        appDb: AppDb,
        storeDao: StoreDao
    ): StoreRepository = StoreRepositoryImpl(storeApiService, appDb, storeDao)

    @Singleton
    @Provides
    fun provideMainRepository(
        storeApiService: StoreApiService
    ): MainRepository = MainRepositoryImpl(storeApiService)
}

/**
 * Finish of STORE section
 */

class HeaderInterceptor(private val configService: ConfigService) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        configService.getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}

