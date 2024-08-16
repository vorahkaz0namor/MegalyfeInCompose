package com.example.megalifecompose.flowBus

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.coroutines.coroutineContext

object FlowBus {
    private val eventFlow = MutableSharedFlow<BusEvent?>()

    suspend fun send(event: BusEvent?) {
        eventFlow.emit(event)
    }

    fun observe(): SharedFlow<BusEvent?> = eventFlow.asSharedFlow()

    suspend inline fun <reified T : BusEvent> subscribe(
        crossinline getEvent: suspend (T) -> Unit
    ) {
        // TODO: Should be there .collectLatest{} ?
        observe().filterIsInstance<T>().conflate().collect { event ->
            coroutineContext.ensureActive()
            getEvent(event)
        }
    }
}