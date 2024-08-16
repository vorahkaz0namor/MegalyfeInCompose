package com.example.megalifecompose.ui.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.ccink.model.dto.Account
import com.ccink.model.dto.Animation
import com.ccink.model.dto.AnimationFile
import com.ccink.model.dto.animationFileGetter
import com.ccink.model.model.PetX
import com.example.megalifecompose.ui.main.store.ErrorDialog
import com.example.megalifecompose.ui.main.store.ProgressBar

sealed interface HomeState {
    data object Loading : HomeState
    data object Error : HomeState
    data class Success(val data: HomeData) : HomeState
}

data class HomeData(
    val account: Account = Account.emptyAccount(),
    val accountPets: List<PetX> = listOf(),
    val animations: List<Animation> = emptyList(),
    val animationFiles: List<AnimationFile> = emptyList()
)

fun HomeData.getAnimationFile(petId: Int): String? =
    animationFiles.animationFileGetter(
        animations = animations,
        accountPetLinks = account.petLinks,
        petId = petId,
        defaultClothesId = null
    )

@Composable
fun HomeState.HandlingLoadState(
    onDismissRequest: () -> Unit,
    composeAfterSuccess: @Composable (HomeData) -> Unit
) {
    Column {
        when (this@HandlingLoadState) {
            is HomeState.Loading -> ProgressBar()
            is HomeState.Error -> ErrorDialog(onDismissRequest = onDismissRequest)
            is HomeState.Success -> composeAfterSuccess(data)
        }
    }
}