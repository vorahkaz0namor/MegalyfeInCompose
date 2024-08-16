package com.example.megalifecompose.ui.first_pers_choosen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.ccink.model.dto.Pet
import com.example.megalifecompose.ui.main.store.ErrorDialog
import com.example.megalifecompose.ui.main.store.ProgressBar

data class FirstPetState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val message: String? = null,
    val success: Boolean = false,
    val pets: SnapshotStateList<ChoosePetModel> = SnapshotStateList()
)

fun FirstPetState.loading() = copy(
    loading = true,
    error = false
)

fun FirstPetState.error(message: String?) = copy(
    loading = false,
    error = true,
    message = message
)

fun FirstPetState.display() = copy(
    loading = false,
    error = false
)

fun FirstPetState.onItemSelected(selectedItem: ChoosePetModel) {
    val iterator = pets.listIterator()
    while (iterator.hasNext()) {
        val listItem = iterator.next()
        iterator.set(
            if (listItem.pet.id == selectedItem.pet.id) {
                listItem.copy(isSelected = true)
            } else {
                listItem.copy(isSelected = false)
            }
        )
    }
}

fun FirstPetState.addItemsToPetList(
    items: List<Pet>
): FirstPetState {
    val statePets = pets.map(ChoosePetModel::pet)
    val iterator = pets.listIterator()
    items.map {
        if (!statePets.contains(it))
            iterator.add(ChoosePetModel(pet = it))
    }
    return this
}

@Composable
fun FirstPetState.StateHandler(
    onDismissError: () -> Unit,
    onSuccess: () -> Unit,
    onCompose: @Composable (FirstPetState) -> Unit
) {
    when {
        loading -> ProgressBar()
        error -> ErrorDialog(
            onDismissRequest = onDismissError,
            message = message
        )
        success -> onSuccess()
        else -> onCompose(this)
    }
}

data class ChoosePetModel(
    val pet: Pet,
    val isSelected: Boolean = false
)