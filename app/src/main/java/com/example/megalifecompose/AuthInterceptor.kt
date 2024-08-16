package com.example.megalifecompose

import coil.intercept.Interceptor
import coil.request.ImageResult
import com.ccink.model.config.ConfigService

class AuthInterceptor(private val configService: ConfigService) : Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val newRequest = chain.request.let { oldRequest ->
            oldRequest.newBuilder(oldRequest.context).let { builder ->
                    configService.getToken()?.let { token ->
                        builder.addHeader(
                            name = "Authorization",
                            value = "Bearer $token"
                        )
                    } ?: builder
                }
                .build()
        }
        return chain.proceed(newRequest)
    }
}