package com.ccink.domain.useCases

import com.ccink.domain.di.IoDispatcher
import com.ccink.domain.getResponse
import com.ccink.model.dto.Account
import com.ccink.model.dto.Animation
import com.ccink.model.dto.AnimationFile
import com.ccink.model.dto.Clothes
import com.ccink.model.model.AccountPetsModel
import com.ccink.model.model.BaseModel
import com.ccink.model.model.ItemRequest
import com.ccink.model.model.Resource
import com.ccink.model.repository.MainRepository
import com.ccink.model.repository.StoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

internal class MainUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository,
    private val storeRepository: StoreRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MainUseCase {

    override suspend fun downloadAnimations(
        fileNames: List<String>
    ): Resource<BaseModel<List<AnimationFile>>> =
        storeRepository.downloadAnimations(fileNames = fileNames).getResponse(dispatcher)

    override suspend fun readAnimations(): Resource<BaseModel<List<Animation>>> =
        storeRepository.readAnimations(request = ItemRequest()).getResponse(dispatcher)

    override suspend fun getAccountPets(): Resource<BaseModel<List<AccountPetsModel>>> =
        mainRepository.getAccountPets().getResponse(dispatcher)

    override suspend fun readAccount(): Resource<BaseModel<Account>> =
        storeRepository.readAccount().getResponse(dispatcher)

    override suspend fun readClothes(): Resource<BaseModel<List<Clothes>>> =
        storeRepository.readClothes(request = ItemRequest()).getResponse(dispatcher)

}

interface MainUseCase {
    suspend fun downloadAnimations(
        fileNames: List<String>
    ): Resource<BaseModel<List<AnimationFile>>>
    suspend fun readAnimations(): Resource<BaseModel<List<Animation>>>
    suspend fun getAccountPets(): Resource<BaseModel<List<AccountPetsModel>>>
    suspend fun readAccount(): Resource<BaseModel<Account>>
    suspend fun readClothes():Resource<BaseModel<List<Clothes>>>
}
