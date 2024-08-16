package com.example.megalifecompose.ui.first_pers_choosen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccink.domain.onFailure
import com.ccink.domain.onSuccess
import com.ccink.domain.useCases.StoreUseCase
import com.ccink.model.model.BaseModel
import com.ccink.model.model.Resource
import com.ccink.resources.IOError
import com.ccink.resources.NoData
import com.example.megalifecompose.App
import com.example.megalifecompose.flowBus.FirstPetEvent
import com.example.megalifecompose.flowBus.FlowBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FirstPersChoosenViewModel : ViewModel() {
    @Inject
    lateinit var storeUseCase: StoreUseCase
    private val _firstPetState: MutableStateFlow<FirstPetState> =
        MutableStateFlow(FirstPetState().loading())
    val firstPetState: StateFlow<FirstPetState>
        get() = _firstPetState.asStateFlow()

    init {
        App.getAppComponent().inject(this)
        subscribeToFlowBus()
        sendEvent(FirstPetEvent.ReadPets)
    }

    fun sendEvent(event: FirstPetEvent) {
        viewModelScope.launch { FlowBus.send(event) }
    }

    private fun subscribeToFlowBus() {
        viewModelScope.launch {
            FlowBus.subscribe<FirstPetEvent> { event ->
                when (event) {
                    is FirstPetEvent.ReadPets -> readPets()
                    is FirstPetEvent.BuyFirstPet ->
                        buyFirstPet(event.petId)
                    is FirstPetEvent.DismissError -> dismissError()
                }
            }
        }
    }

    private suspend fun readPets() {
        storeUseCase.readPets().responseHandler { pets ->
            _firstPetState.value =
                firstPetState.value
                    .addItemsToPetList(pets)
                    .display()
        }
    }

    private suspend fun buyFirstPet(petId: Int) {
            storeUseCase.buyPet(petId = petId).responseHandler {
                _firstPetState.value =
                    firstPetState.value.copy(success = true)
            }
    }

    private fun dismissError() {
        _firstPetState.value =
            firstPetState.value.display()
    }

    private inline fun <T> Resource<BaseModel<T>>
            .responseHandler(action: (T) -> Unit) {
        onSuccess {
            it.data?.let { action(it) }
                ?: firstPetState.value.error(NoData).let {
                    _firstPetState.value = it
                }
        }
        onFailure {
            _firstPetState.value =
                firstPetState.value.error(IOError)
        }
    }
}