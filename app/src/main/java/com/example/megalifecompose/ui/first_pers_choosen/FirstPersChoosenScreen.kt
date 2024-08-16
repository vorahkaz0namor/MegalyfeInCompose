package com.example.megalifecompose.ui.first_pers_choosen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.ccink.common.base_views.BaseMainBtn
import com.ccink.common.base_views.BaseSpacer
import com.ccink.resources.EightDp
import com.ccink.resources.R
import com.ccink.resources.TwentyFourDp
import com.ccink.resources.colors
import com.ccink.resources.styles
import com.example.megalifecompose.flowBus.FirstPetEvent
import com.example.megalifecompose.navigation.AuthScreen
import com.example.megalifecompose.ui.first_pers_choosen.ItemDataState.PersFirstModel
import com.example.megalifecompose.ui.main.store.ProgressBar
import com.example.megalifecompose.ui.main.store.getItemBackground
import com.example.megalifecompose.ui.main.store.sampleItems

@Composable
@Preview(showBackground = true)
private fun MainScreenPreview() {
    MainScreen(FirstPetState())
}

@Composable
@Preview
private fun PersItemPreview() {
    PersItem()
}

@Composable
private fun PersItem(
    item: ChoosePetModel = ChoosePetModel(
        pet = sampleItems().pets.first()
    ),
    onSelected: (ChoosePetModel) -> Unit = {},
) {
    val pet = item.pet

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelected(item)
            }
            .padding(vertical = 12.dp)
    ) {

        val (pers, container, spacer) = createRefs()

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(26.dp)
            .constrainAs(spacer) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, shape = RoundedCornerShape(14.dp))
                .background(
                    if (item.isSelected) MaterialTheme.colors.colorB066FF else Color.White,
                    RoundedCornerShape(14.dp)
                )
                .constrainAs(container) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    top.linkTo(spacer.bottom)
                    height = Dimension.wrapContent
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(114.dp)
            )

            Column(
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        end = 16.dp,
                        bottom = TwentyFourDp
                    )
            ) {
                Text(
                    text = pet.name,
                    style = MaterialTheme.styles.s17h20w600,
                    color = if (item.isSelected) Color.White else MaterialTheme.colors.color212529
                )

                BaseSpacer(height = 13.dp)

                Text(
                    text = pet.description,
                    style = MaterialTheme.styles.s11h13w600,
                    color = if (item.isSelected) Color.White else MaterialTheme.colors.color212529.copy(0.3f)
                )
            }
        }


        Box(
            modifier = Modifier
                .height(150.dp)
                .width(98.dp)
                .background(pet.getItemBackground(), RoundedCornerShape(10.dp))
                .constrainAs(pers) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    translationX = 16.dp
                },
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.padding(vertical = TwentyFourDp),
                contentScale = ContentScale.FillHeight,
                model = pet.image,
                contentDescription = null,
                loading = { ProgressBar() }
            )
        }


    }


}

@Composable
fun FirstPersChoosenRoute(
    navController: NavController = rememberNavController(),
    firstPersChoosenViewModel: FirstPersChoosenViewModel =
        viewModel<FirstPersChoosenViewModel>()
) {
    val state by firstPersChoosenViewModel.firstPetState.collectAsStateWithLifecycle()

    state.StateHandler(
        onDismissError = {
            firstPersChoosenViewModel.sendEvent(
                FirstPetEvent.DismissError
            )
        },
        onSuccess = {
            navController.navigate(route = AuthScreen.MAIN_SCREEN)
        }
    ) {
        MainScreen(
            state = state,
            onBuyFirstPet = { petId: Int ->
                firstPersChoosenViewModel.sendEvent(
                    FirstPetEvent.BuyFirstPet(petId)
                )
            }
        )
    }
}

@Composable
private fun MainScreen(
    state: FirstPetState,
    onBuyFirstPet: (Int) -> Unit = {}
) {
    var item: ChoosePetModel? by remember {
        mutableStateOf(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {

        BaseSpacer(height = 43.dp)

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {

            Text(
                text = "Выбор персонажа",
                style = MaterialTheme.styles.s24h29w600,
                color = MaterialTheme.colors.color212529
            )

            BaseSpacer(height = 10.dp)

            Text(
                text = "Далее ты сможешь обменять его или улучшить комплект одежды в магазине",
                style = MaterialTheme.styles.s14h17w400,
                color = MaterialTheme.colors.color212529.copy(alpha = 0.5f)
            )
        }

        BaseSpacer(height = 14.dp)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colors.color212529.copy(alpha = 0.1f))
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {

            LazyColumn(contentPadding = PaddingValues(top = 18.dp)) {

                items(state.pets, key = { it.pet.id }) {
                    PersItem(it) { pers ->
                        item = pers
                        state.onItemSelected(pers)
                    }
                }

                item {
                    BaseSpacer(height = 40.dp)

                    BaseMainBtn(title = "Выбрать", item != null) {
                        item?.let { onBuyFirstPet(it.pet.id) }
                    }
                }

            }

        }


    }

}




