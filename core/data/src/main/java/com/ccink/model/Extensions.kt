package com.ccink.model

import com.ccink.model.model.BaseModel
import com.ccink.model.model.Resource
import com.ccink.model.model.ResponseModel
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK
import java.net.UnknownHostException

internal suspend fun <T : ResponseModel> handleAPICall(
    apiCall: suspend () -> Response<T>,
): Resource<T> {
    return try {
        apiCall.invoke().handleAPIResponse()
    } catch (e: Exception) {
        e.printStackTrace()
        return when (e) {
            is UnknownHostException -> isFailure()
            else -> isFailure()
        }
    }
}

internal suspend fun <T> handleDAOCall(
    daoCall: suspend () -> T?
): Resource<BaseModel<T>> =
    try {
        Resource.Success(
            BaseModel(
                status = HTTP_OK,
                data = daoCall()
            )
        )
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.Failure(IOException())
    }

internal suspend fun <T, R : Response<BaseModel<T>>> R.handleWithDao(
    action: suspend (T) -> Unit
): R {
    also { response ->
        response.body()?.data?.let {
            action(it)
        }
    }
    return this
}

private fun <T : ResponseModel> Response<T>.handleAPIResponse(): Resource<T> {
    val responseBody = body() ?: return isFailure()
    if (isSuccess()) {
        return Resource.Success(responseBody)
    }
    return isFailure()
}

private fun <T : ResponseModel> Response<T>.isSuccess(): Boolean = isSuccessful

private fun <T : ResponseModel> isFailure(): Resource<T> =
    Resource.Failure(IOException())