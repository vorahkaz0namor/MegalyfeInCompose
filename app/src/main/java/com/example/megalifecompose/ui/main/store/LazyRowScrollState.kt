package com.example.megalifecompose.ui.main.store

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.saveable.rememberSaveable

/**
 * Remembered positions of LazyLists
 */
private val PositionMap = mutableMapOf<String, Position>()

/**
 * Position entity
 */
private data class Position(
    val index: Int,
    val scrollOffset: Int
)

@Composable
fun rememberForeverLazyListState(
    onDisposeKey: Any? = Unit,
    positionKey: String,
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemScrollOffset: Int = 0
): LazyListState {
    val scrollState = rememberSaveable(saver = LazyListState.Saver) {
        val savedPosition = PositionMap[positionKey]
        LazyListState(
            firstVisibleItemIndex =
                savedPosition?.index
                    ?: initialFirstVisibleItemIndex,
            firstVisibleItemScrollOffset =
                savedPosition?.scrollOffset
                    ?: initialFirstVisibleItemScrollOffset
        )
    }

    DisposableEffect(key1 = onDisposeKey) {
        onDispose {
            PositionMap[positionKey] =
                Position(
                    index = scrollState.firstVisibleItemIndex,
                    scrollOffset = scrollState.firstVisibleItemScrollOffset
                )
        }
    }
    return scrollState
}