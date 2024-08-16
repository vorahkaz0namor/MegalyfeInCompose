package com.example.megalifecompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ccink.common.extations.navigateWithPopUpTo
import com.example.megalifecompose.ui.main.store.WithCallbackState
import com.example.megalifecompose.ui.main.store.ItemScreenInStore
import com.example.megalifecompose.ui.main.store.StoreMainScreen
import com.example.megalifecompose.ui.main.store.StoreViewModel

@Composable
fun StoreNavHost(
    storeNavHostController: NavHostController = rememberNavController(),
    storeViewModel: StoreViewModel = viewModel<StoreViewModel>()
) {
    storeNavHostController
        .HelperForStoreViewModel { isItemScreen, navigateUp, navigateToItemScreen ->
            storeViewModel.WithCallbackState(
                isItemScreen = isItemScreen,
                navigateUp = navigateUp,
                navigateToItemScreen = navigateToItemScreen
            ) { storeState, callbackState ->
                NavHost(
                    navController = storeNavHostController,
                    startDestination = StoreScreen.StoreMain.route
                ) {
                    composable(route = StoreScreen.StoreMain.route) {
                        StoreMainScreen(
                            storeState = storeState,
                            callbackState = callbackState
                        )
                    }
                    composable(route = StoreScreen.Item.route) {
                        storeState.selectedItem?.let {
                            ItemScreenInStore(
                                storeState = storeState,
                                callbackState = callbackState
                            )
                        }
                    }
                }
            }
        }
}

sealed class StoreScreen(val route: String) {

    companion object {
        const val STORE_MAIN_SCREEN = "store_main_screen"
        const val ITEM_SCREEN = "item_screen"
    }

    data object StoreMain : StoreScreen(STORE_MAIN_SCREEN)
    data object Item : StoreScreen(ITEM_SCREEN)
}

@Composable
fun NavHostController.HelperForStoreViewModel(
    composable: @Composable (() -> Boolean, () -> Boolean, () -> Unit) -> Unit
) {
    val isItemScreen = {
        currentDestination?.route == StoreScreen.Item.route
    }
    val navigateUp = { navigateUp() }
    val navigateToItemScreen = { navigateWithPopUpTo(route = StoreScreen.Item.route) }
    composable(isItemScreen, navigateUp, navigateToItemScreen)
}