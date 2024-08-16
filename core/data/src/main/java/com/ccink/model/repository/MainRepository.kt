package com.ccink.model.repository

import com.ccink.model.handleAPICall
import com.ccink.model.model.AccountPetsModel
import com.ccink.model.model.BaseModel
import com.ccink.model.model.Resource
import com.ccink.model.network.StoreApiService
import javax.inject.Inject

internal class MainRepositoryImpl @Inject constructor(
    private val storeApiService: StoreApiService
) : MainRepository {
    override suspend fun getAccountPets(): Resource<BaseModel<List<AccountPetsModel>>> {
        return handleAPICall { storeApiService.getAccountPets() }
    }
}

interface MainRepository {
    suspend fun getAccountPets(): Resource<BaseModel<List<AccountPetsModel>>>
}