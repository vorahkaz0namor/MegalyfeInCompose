package com.example.megalifecompose

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.example.megalifecompose.di.AppComponent
import com.example.megalifecompose.di.DaggerAppComponent

class App : Application(), ImageLoaderFactory {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getAppComponent(): AppComponent = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().app(this).build()
    }

    override fun newImageLoader(): ImageLoader =
        ImageLoader(this).newBuilder()
            .components {
                add(AuthInterceptor(appComponent.configService()))
                add(SvgDecoder.Factory())
            }
            /**
             * TODO: Unfortunately, either memory cache and disk cache
             *       don't works when app is shutdown and then start again
             **/
//            .memoryCachePolicy(CachePolicy.ENABLED)
//            .memoryCache {
//                MemoryCache.Builder(this)
//                    .maxSizePercent(0.1)
//                    .strongReferencesEnabled(true)
//                    .build()
//            }
//            .diskCachePolicy(CachePolicy.ENABLED)
//            .diskCache {
//                DiskCache.Builder()
//                    .maxSizePercent(0.05)
//                    .directory(cacheDir)
//                    .build()
//            }
            .build()
}