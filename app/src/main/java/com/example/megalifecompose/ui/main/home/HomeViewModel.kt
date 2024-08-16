package com.example.megalifecompose.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccink.domain.onFailure
import com.ccink.domain.onSuccess
import com.ccink.domain.useCases.MainUseCase
import com.ccink.model.dto.Animation
import com.ccink.model.dto.getFileNames
import com.ccink.model.model.BaseModel
import com.ccink.model.model.Resource
import com.example.megalifecompose.App
import com.example.megalifecompose.flowBus.FlowBus
import com.example.megalifecompose.flowBus.HomeEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var mainUseCase: MainUseCase

    private val _state: MutableStateFlow<HomeState?> = MutableStateFlow(null)
    private var _homeData: HomeData = HomeData()
    val state: StateFlow<HomeState?> = _state.asStateFlow()

    init {
        App.getAppComponent().inject(this)
        loadUser()
        subscribeToFlowBus()
    }

    fun sendEvent(event: HomeEvent) {
        viewModelScope.launch { FlowBus.send(event) }
    }

    private fun subscribeToFlowBus() {
        viewModelScope.launch {
            FlowBus.subscribe<HomeEvent> { event ->
                Log.i("CATCH EVENT IN HOME", event.toString())
                when (event) {
                    is HomeEvent.RefreshHome -> {
                        _state.value = HomeState.Loading
                        refreshAccount()
                    }
                }
            }
        }
    }

    private suspend fun refreshAccount(animations: List<Animation>? = null) {
            mainUseCase.apply {
                readAccount().fetchData { account ->

                    getAccountPets().fetchData { accountPets ->

                        downloadAnimations(
                            (animations ?: _homeData.animations)
                                .getFileNames(account.petLinks)
                        ).fetchData { animationFiles ->

                            _homeData
                                .let {
                                    if (animations != null)
                                        it.copy(animations = animations)
                                    else
                                        it
                                }
                                .copy(
                                    account = account,
                                    accountPets = accountPets.map { it.pet },
                                    animationFiles = animationFiles
                                ).updateHomeData()
                        }
                    }
                }
            }
        }

    private fun loadUser() =
        executeTheAction {
            mainUseCase.apply {
                readClothes().fetchData {

                            readAnimations().fetchData { animations ->

                                    refreshAccount(animations)
                            }
                }
            }
        }

    /**
     * Updates HomeData if HomeState is Success
     */
    private fun HomeData.updateHomeData() {
        _homeData = this
        _state.value = HomeState.Success(this)
    }

    /**
     * Executes the action has called by the FlowBus event
     */
    private fun HomeViewModel.executeTheAction(
        action: suspend () -> Unit
    ): Job =
        with(viewModelScope) {
            _state.value = HomeState.Loading
            launch { action() }
        }

    /**
     * Fetching incoming data
     */
    private inline fun <T> Resource<BaseModel<T>>.fetchData(action: (T) -> Unit) {
        onSuccess {
            it.data?.let { action(it) }
                ?: HomeState.Error.let { _state.value = it }
        }
            .onFailure {
                _state.value = HomeState.Error
            }
    }
}