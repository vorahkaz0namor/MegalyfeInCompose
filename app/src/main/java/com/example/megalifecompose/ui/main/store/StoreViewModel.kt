package com.example.megalifecompose.ui.main.store

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.ccink.domain.onFailure
import com.ccink.domain.onSuccess
import com.ccink.domain.useCases.StoreUseCase
import com.ccink.model.dto.Clothes
import com.ccink.model.dto.FeedItem
import com.ccink.model.dto.ImageFile
import com.ccink.model.dto.MegalifeImageRequest
import com.ccink.model.dto.Pet
import com.ccink.model.dto.PetLink
import com.ccink.model.dto.getAllFileNames
import com.ccink.model.dto.hasWornOn
import com.ccink.model.model.BaseModel
import com.ccink.model.model.Resource
import com.ccink.resources.*
import com.example.megalifecompose.App
import com.example.megalifecompose.flowBus.FlowBus
import com.example.megalifecompose.flowBus.HomeEvent
import com.example.megalifecompose.flowBus.StoreEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoreViewModel : ViewModel() {
    @Inject
    lateinit var storeUseCase: StoreUseCase
    private val _previousStoreState: MutableStateFlow<StoreState> =
        MutableStateFlow(StoreState())
    private val _currentStoreState: MutableStateFlow<StoreState> =
        MutableStateFlow(StoreState().loading())
    val storeState: StateFlow<StoreState>
        get() = _currentStoreState.asStateFlow()
    private val _selectedItem = MutableStateFlow<FeedItem?>(null)
    private val _activePetId = MutableStateFlow(
        storeState.value.pets.firstOrNull()?.id
            ?: _previousStoreState.value.activePetId
    )
    /**
     * Keeps current StoreEvent to repeat it when its failed
     */
    private val _currentEvent = MutableStateFlow<StoreEvent>(StoreEvent.LoadData)

    init {
        App.getAppComponent().inject(this)
        setSelectedItem()
        setActivePetId()
        storeState.collectStoreState()
        subscribeToFlowBus()
        sendEvent(StoreEvent.LoadData)
    }

    fun sendEvent(event: StoreEvent) {
        viewModelScope.launch { FlowBus.send(event) }
    }

    private fun confirmError() {
        sendEvent(_currentEvent.value)
    }

    private fun setActivePetId() {
        viewModelScope.launch {
            _activePetId.collectLatest {
                storeState.value.copy(
                    activePetId = it
                ).updateStoreState()
            }
        }
    }

    private fun setActivePetIdFromEvent(id: Int) {
        viewModelScope.launch {
            _activePetId.value = id
        }
    }

    private suspend fun backToStoreMainScreen() {
        _selectedItem.value = null
        selectChooserItemTemplate(ItemInformation)
        storeUseCase.readAccount().handlingIncomingData {
            storeState.value.copy(account = it)
                .updateStoreState()
        }
    }

    private fun setDefaultChooserItem() {
        viewModelScope.launch {
            selectChooserItemTemplate(ItemInformation)
        }
    }

    private suspend fun selectChooserItem(
        desc: String,
        itemClass: Class<out FeedItem>,
        itemId: Int
    ) {
            selectChooserItemTemplate(desc)
            storeState.value.viewChooserItems.find { it.isSelected }
                ?.let {
                    when (it.description) {
                        ItemInformation -> {
                            if (itemClass == Pet::class.java)
                                showOnlyPetSuspend(itemId)
                            else
                                showOnlyClothesSuspend(itemId)
                        }
                        SuitableClothes -> {
                            storeUseCase.readClothes().handlingIncomingData {
                                storeState.value.copy(
                                    clothes = it.sortedBy { it.id }
                                ).updateStoreState()
                            }
                        }
                        CompatibleCharacters -> {
                            storeUseCase.readPets().handlingIncomingData {
                                storeState.value.copy(
                                    pets = it.sortedBy { it.id }
                                ).updateStoreState()
                            }
                        }
                    }
                }
    }

    private fun selectChooserItemTemplate(desc: String) {
        storeState.value.copy(
            viewChooserItems = storeState.value.viewChooserItems.map { chooserItem ->
                if (chooserItem.description == desc)
                    chooserItem.select()
                else
                    chooserItem.deselect()
            }
        ).updateStoreState()
    }

    private suspend fun showOnlyPetSuspend(id: Int) {
        storeUseCase.readSinglePet(petId = id).handlingIncomingData {
            _selectedItem.value = it
            storeUseCase.readAccount().handlingIncomingData {
                storeState.value.copy(account = it)
                    .updateStoreState()
            }
        }
    }

    private suspend fun buyPet(id: Int) {
            storeUseCase.buyPet(petId = id).handlingIncomingData {
                storeUseCase.readAccount().handlingIncomingData {
                    storeState.value.copy(
                        account = it,
                        boughtItem =
                            storeState.value.pets
                                .find { it.id == id }
                    ).updateStoreState()
                    openDialog()
                }
            }
    }

    private suspend fun showOnlyClothesSuspend(id: Int) {
        storeUseCase.readSingleClothes(clothesId = id).handlingIncomingData {
            _selectedItem.value = it
            storeUseCase.readAccount().handlingIncomingData {
                storeState.value.copy(account = it)
                    .updateStoreState()
            }
        }
    }

    private suspend fun buyClothes(id: Int) {
            storeUseCase.buyClothes(clothesId = id).handlingIncomingData {
                storeUseCase.readAccount().handlingIncomingData {
                    storeState.value.copy(
                        account = it,
                        boughtItem =
                        storeState.value.clothes
                            .find { it.id == id }
                    ).updateStoreState()
                    openDialog()
                }
            }
    }

    private suspend fun putOnSingleClothesOnPet(clothesId: Int, petId: Int) {
            storeUseCase.putOnSingleClothesOnPet(
                clothesId = clothesId,
                petId = petId
            ).handlingIncomingData {
                    storeUseCase.readAccount().handlingIncomingData {
                        storeState.value.copy(account = it)
                            .updateStoreState()
                    }
                }
    }

    private suspend fun saveImageFile(
        item: FeedItem,
        image: Drawable?
    ) {
//        Log.d("LET'S SAVE THE IMAGE",
//            "image = ${image.toString()}")
        image?.let { imageToSave ->
            val boughtPet = storeState.value.account.petLinks
                .find { it.petId == (item as? Pet)?.id }
            if (storeState.value.getItemFile(item) == null) {
//                Log.d("IT'S A NEW IMAGE FOR DB",
//                    "itemId = ${boughtPet?.petId ?: item.id}\n" +
//                            "clothesId (if item is Pet) = ${boughtPet?.fullId
//                                ?: (item as? Pet)?.defaultClothesId}")
                val imageFile = when {
                    boughtPet != null -> ImageFile(
                        petId = boughtPet.petId,
                        clothesId = boughtPet.fullId,
                        image = imageToSave
                    )
                    item is Pet -> ImageFile(
                        petId = item.id,
                        clothesId = item.defaultClothesId,
                        fileName = item.fileName,
                        image = imageToSave
                    )
                    else -> ImageFile(
                        clothesId = item.id,
                        fileName = item.fileName,
                        image = imageToSave
                    )
                }
                storeUseCase.saveImageFile(imageFile = imageFile)
                    .handlingIncomingData {
//                        Log.d("IMAGE WAS SAVED SUCCESSFULLY",
//                            it.toString())
                        storeState.value.copy(
                            imageFiles =
                            storeState.value.imageFiles
                                .plus(imageFile)
                        ).updateStoreState()
                    }
            }
        } ?: Log.d("WARNING!", "image is null")
    }

    private suspend fun getImageRequest(petLink: PetLink) {
//        Log.d("CREATE IMAGE REQUEST",
//            "petId = ${petLink.petId}\n" +
//                    "clothesId (fullId) = ${petLink.fullId}")
        storeUseCase.getImageRequest(
            petId = petLink.petId,
            clothesId = petLink.fullId
        )
            .handlingIncomingData { imageRequest ->
//                Log.d("HERE GOES CREATED REQUEST",
//                    imageRequest)
                storeState.value.copy(
                    imageRequests =
                        storeState.value.imageRequests
                            .plus(
                                MegalifeImageRequest(
                                    petId = petLink.petId,
                                    clothesId = petLink.fullId,
                                    request = imageRequest
                                )
                            )
                ).updateStoreState()
        }
    }

    private fun openDialog() {
            storeState.value
                .copy(openDialog = true)
                .updateStoreState()
    }

    private fun clearOpenDialog() {
            storeState.value.copy(
                openDialog = false,
                boughtItem = null
            ).updateStoreState()
    }

    private fun setSelectedItem() {
        viewModelScope.launch {
            _selectedItem.collectLatest {
                storeState.value
                    .copy(selectedItem = it)
                    .updateStoreState()
            }
        }
    }

    private suspend fun loadData() {
        storeUseCase.apply {
            readClothes().handlingIncomingData { clothes ->

                readPets().handlingIncomingData { pets ->

                    readAnimations().handlingIncomingData { animations ->

                        getImageFiles().handlingIncomingData { imageFiles ->

                            downloadAnimations(animations.getAllFileNames())
                                .handlingIncomingData { animationsFiles ->

                                    readAccount().handlingIncomingData { account ->

                                        storeState.value.copy(
                                            pets = pets.sortedBy { it.id },
                                            clothes = clothes.sortedBy { it.id },
                                            animations = animations,
                                            animationFiles = animationsFiles,
                                            imageFiles = imageFiles,
                                            account = account
                                        ).updateStoreState()
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    private fun subscribeToFlowBus() {
        viewModelScope.launch {
            FlowBus.subscribe<StoreEvent> { event ->
                when (event) {
                    is StoreEvent.ConfirmError ->
                        confirmError()
                    else -> {
                        _currentEvent.value = event
                        when (event) {
                            is StoreEvent.LoadData ->
                                executeTheAction { loadData() }
                            is StoreEvent.ShowPetCard ->
                                executeTheAction { showOnlyPetSuspend(event.id) }
                            is StoreEvent.BuyPet ->
                                executeTheAction { buyPet(event.id) }
                            is StoreEvent.ShowClothesCard ->
                                executeTheAction { showOnlyClothesSuspend(event.id) }
                            is StoreEvent.BuyClothes ->
                                executeTheAction { buyClothes(event.id) }
                            is StoreEvent.PutOnSingleClothesOnPet ->
                                executeTheAction {
                                    putOnSingleClothesOnPet(
                                        clothesId = event.clothesId,
                                        petId = event.petId
                                    )
                                }
                            is StoreEvent.SaveImageFile ->
                                saveImageFile(
                                    item = event.item,
                                    image = event.image
                                )
                            is StoreEvent.GetImageRequest ->
                                getImageRequest(petLink = event.petLink)
                            is StoreEvent.ClearOpenDialog ->
                                executeTheAction { clearOpenDialog() }
                            is StoreEvent.SelectChooserItem ->
                                executeTheAction {
                                    selectChooserItem(
                                        desc = event.description,
                                        itemClass = event.itemClass,
                                        itemId = event.itemId
                                    )
                                }
                            is StoreEvent.SetDefaultChooserItem ->
                                setDefaultChooserItem()
                            is StoreEvent.BackToStoreMainScreen ->
                                executeTheAction { backToStoreMainScreen() }
                            is StoreEvent.SetActivePetId ->
                                setActivePetIdFromEvent(event.id)
                            else -> { /* TODO: Nothing */ }
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates store state
     */
    private fun StoreState.updateStoreState() {
        _previousStoreState.value = _currentStoreState.value
        _currentStoreState.value = this
    }

    /**
     * Collects store state when it has updated
     */
    private fun StateFlow<StoreState>.collectStoreState() {
        viewModelScope.launch {
            this@collectStoreState.collectLatest {
                if (it.account != _previousStoreState.value.account)
                    FlowBus.send(HomeEvent.RefreshHome)
            }
        }
    }

    private inline fun StoreViewModel.executeTheAction(
        action: () -> Unit
    ) {
            storeState.value.loading().updateStoreState()
            action()
            if (!storeState.value.error) {
                storeState.value.display().updateStoreState()
            }
    }

    /**
     * Handling UseCase response depending on Success or Failure
     */
    private inline fun <T> Resource<BaseModel<T>>
            .handlingIncomingData(action: (value: T) -> Unit) {
        onSuccess {
            it.data?.let { action(it) }
                ?: storeState.value.error(NoData).updateStoreState()
        }
            .onFailure {
                storeState.value.error(IOError).updateStoreState()
            }
    }
}

@Composable
fun StoreViewModel.WithCallbackState(
    isItemScreen: () -> Boolean,
    navigateUp: () -> Boolean,
    navigateToItemScreen: () -> Unit,
    composable: @Composable (StoreState, CallbackState) -> Unit
) {
    val storeState by storeState.collectAsStateWithLifecycle()
    val callbackState = CallbackState(
        confirmError = { sendEvent(StoreEvent.ConfirmError) },
        clearOpenDialog = { sendEvent(StoreEvent.ClearOpenDialog) },
        onBackButtonClick = {
            sendEvent(StoreEvent.BackToStoreMainScreen)
            navigateUp()
        },
        isItemScreen = isItemScreen,
        setItemState = { itemClass: Class<out FeedItem>, itemId: Int, itemPrice: Int ->
            val account = storeState.account
            val boughtPet = account.petLinks.find {
                if (isItemScreen())
                    it.petId == storeState.selectedItem?.id
                else
                    it.petId == storeState.activePetId
            }
            val isBought = if (itemClass == Pet::class.java)
                account.petLinks.find { it.petId == itemId } != null
            else
                account.clothesIds.find { it == itemId } != null
            val isWornOn = itemClass == Clothes::class.java &&
                    boughtPet?.hasWornOn(itemId) ?: false
            val petIsBought = boughtPet != null
            val itemAllowedToBuy = !isBought && account.balance >= itemPrice
            val clothesAllowedToBuyDependingBoughtPet =
                if (itemClass == Clothes::class.java) {
                    if (itemClass != storeState.selectedItem?.let { it::class.java })
                        petIsBought
                    else {
                        val compatiblePetId = storeState.pets
                            .find { it.clothesIds.contains(itemId) }
                            ?.id
                        account.petLinks
                            .find { it.petId == compatiblePetId } != null
                    }
                }
                else
                    true
            ItemButtonsState(
                purchaseButtonVisibility =
                when {
                    itemClass == Pet::class.java -> true
                    !isBought -> true
                    else -> false
                },
                putOnButtonVisibility =
                when {
                    itemClass == Pet::class.java -> false
                    isBought -> true
                    else -> false
                },
                purchaseButtonCaption =
                if (isBought) AlreadyBought else itemPrice.toString(),
                putOnButtonCaption =
                if (isBought)
                    if (isWornOn) HasWornOn else WearItOn
                else
                    "",
                shouldEnablePurchaseButton =
                itemAllowedToBuy &&
                clothesAllowedToBuyDependingBoughtPet,
                shouldEnablePutOnButton = petIsBought && !isWornOn
            )
        },
        onChooserItemClick = { desc: String, itemClass: Class<out FeedItem>, itemId: Int ->
            sendEvent(
                StoreEvent.SelectChooserItem(
                    description = desc,
                    itemClass = itemClass,
                    itemId = itemId
                )
            )
        },
        onSelectedItemClick = { itemClass: Class<out FeedItem>, itemId: Int ->
            if (isItemScreen())
                sendEvent(StoreEvent.SetDefaultChooserItem)
            sendEvent(
                if (itemClass == Pet::class.java)
                    StoreEvent.ShowPetCard(id = itemId)
                else
                    StoreEvent.ShowClothesCard(id = itemId)
            )
            navigateToItemScreen()
        },
        setActivePetId = { petId: Int ->
            if (!isItemScreen())
                sendEvent(StoreEvent.SetActivePetId(id = petId))
        },
        onBuyItemClick = { itemClass: Class<out FeedItem>, itemId: Int ->
            sendEvent(
                if (itemClass == Pet::class.java)
                    StoreEvent.BuyPet(id = itemId)
                else
                    StoreEvent.BuyClothes(id = itemId)
            )
        },
        putOnClothes = { petId: Int?, clothesId: Int? ->
            if (petId != null && clothesId != null)
                sendEvent(
                    StoreEvent.PutOnSingleClothesOnPet(
                        clothesId = clothesId,
                        petId = petId
                    )
                )
        },
        getItemFile = { item: FeedItem ->
            storeState.getItemFile(item)
        },
        getImageRequest = { item: FeedItem ->
                val boughtPetLink = storeState.account.petLinks
                        .find { it.petId == (item as? Pet)?.id }
                if (boughtPetLink != null) {
                    val foundRequest = {
                        storeState.imageRequests
                            .find {
                                (it.petId == boughtPetLink.petId &&
                                        it.clothesId == boughtPetLink.fullId)
                            }
                            ?.request
                    }
                    foundRequest()?.let {
//                        Log.d("IMAGE REQUEST ALREADY EXIST",
//                            "$it was created earlier")
                        it
                    } ?: let {
                        sendEvent(
                            StoreEvent.GetImageRequest(boughtPetLink)
                        )
                        foundRequest()
                    }
                } else
                    item.image
        },
        saveItemImage = { item, drawable ->
            sendEvent(
                StoreEvent.SaveImageFile(
                    item = item,
                    image = drawable
                )
            )
        }
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =
            if (storeState.loading)
                Arrangement.Center
            else
                Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            /**
             * TODO: When UI adaptation would be resolved then should kill this CRUTCH
             */
            .verticalScroll(state = rememberScrollState())
    ) {
        storeState.HandlingLoadState(
            callbackState = callbackState,
            putOnClothes =
            if (!isItemScreen())
                storeState.addActivePetIdToPutOnClothes(callbackState.putOnClothes)
            else
                storeState.addSelectedPetIdToPutOnClothes(callbackState.putOnClothes)
        ) { composable(storeState, callbackState) }
    }
}