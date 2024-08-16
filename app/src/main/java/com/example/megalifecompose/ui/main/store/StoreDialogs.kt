package com.example.megalifecompose.ui.main.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ccink.model.dto.Clothes
import com.ccink.model.dto.FeedItem
import com.ccink.model.dto.Pet
import com.ccink.resources.*

@Preview
@Composable
fun PreviewAfterPurchaseCharacterDialog() {
    MegaLifeComposeTheme {
        AfterPurchaseDialog(
            callbackState = callbackStateStub,
            putOnClothes = {},
            item = sampleItems().pets.last(),
            wearOnButtonIsVisible = true
        )
    }
}

@Preview
@Composable
fun PreviewAfterPurchaseClothesDialog() {
    MegaLifeComposeTheme {
        AfterPurchaseDialog(
            callbackState = callbackStateStub,
            putOnClothes = {},
            item = sampleItems().clothes[0],
            wearOnButtonIsVisible = true
        )
    }
}

@Preview
@Composable
fun PreviewErrorDialog() {
    ErrorDialog(onDismissRequest = {})
}

@Composable
fun ProgressBar() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.progress_bar_three)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            modifier = Modifier.size(FiftySixDp)
        )
    }
}

@Composable
fun AfterPurchaseDialog(
    callbackState: CallbackState,
    putOnClothes: (Int?) -> Unit,
    item: FeedItem,
    wearOnButtonIsVisible: Boolean
) {
    Dialog(onDismissRequest = callbackState.clearOpenDialog) {
        val itemCardModifier: Modifier
        val imageModifier: Modifier
        when (item) {
            is Pet-> {
                itemCardModifier = Modifier
                    .shadow(
                        elevation = TwentyFourDpClean,
                        spotColor = MaterialTheme.colors.color1F18274B,
                        ambientColor = MaterialTheme.colors.color1F18274B
                    )
                    .height(TwoHundredSixteenDpClean)
                    .width(OneHundredFiftyThreeDpClean)
                imageModifier = Modifier
                    .height(OneHundredSeventyEightDpClean)
                    .width(SeventySixDpClean)
            }
            is Clothes -> {
                itemCardModifier = Modifier
                    .shadow(
                        elevation = EightDpClean,
                        spotColor = MaterialTheme.colors.color1418274B,
                        ambientColor = MaterialTheme.colors.color1418274B
                    )
                    .height(OneHundredThirtyEightDpClean)
                    .width(OneHundredTwentyEightDpClean)
                imageModifier = Modifier
                    .height(NinetyEightDpClean)
            }
        }

        Card(
            modifier = Modifier
                .width(TwoHundredSeventyEightDpClean),
            shape = RoundedCornerShape(TwentyDpClean),
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colors.white
            )
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(
                        horizontal = SixteenDpClean,
                        vertical = TwentyFourDpClean
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /**
                 * Item image
                 */
                Card {
                    Column(
                        modifier = itemCardModifier
                            .background(item.getItemBackground()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ItemImageOnly(
                            modifier = imageModifier,
                            contentScale = when (item) {
                                is Pet -> ContentScale.FillHeight
                                is Clothes -> ContentScale.Fit
                            },
                            item = item,
                            callbackState = callbackState
                        )
                    }
                }

                /**
                 * Item purchase confirmation text
                 */
                Text(
                    modifier = Modifier.padding(top = SixteenDpClean),
                    text = when (item) {
                        is Pet -> CharacterPurchaseConfirmation
                        is Clothes -> ClothesPurchaseConfirmation
                    },
                    fontSize = SixteenSpClean,
                    color = MaterialTheme.colors.color75212529,
                    textAlign = TextAlign.Center
                )

                /**
                 * Item name
                 */
                Text(
                    modifier = Modifier.padding(top = FourDpClean),
                    text = item.name,
                    fontSize = TwentyFourSpClean,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.color212529,
                    textAlign = TextAlign.Center
                )

                /**
                 * Confirm item purchase button
                 */
                Button(
                    onClick = { callbackState.clearOpenDialog() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = TwentyFourDpClean),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BtnEnabled,
                    ),
                    shape = RoundedCornerShape(TenDpClean)
                ) {
                        Text(
                            text = when (item) {
                                is Pet -> Happiness
                                is Clothes -> MeanIt
                            },
                            style = TextStyle(
                                fontSize = SixteenSpClean,
                                color = Color.White,
                            )
                        )
                }

                /**
                 * WearOn clothes button
                 */
                ConstraintLayout {
                    Button(
                        onClick = {
                            putOnClothes(
                                if (item is Clothes) item.id else null
                            )
                            callbackState.clearOpenDialog()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = EightDpClean)
                            .constrainAs(createRef()) {
                                visibility = if (wearOnButtonIsVisible)
                                    Visibility.Visible
                                else
                                    Visibility.Gone
                            },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BtnEnabled,
                        ),
                        shape = RoundedCornerShape(TenDpClean)
                    ) {
                        Text(
                            text = WearItOn,
                            style = TextStyle(
                                fontSize = SixteenSpClean,
                                color = Color.White,
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorDialog(
    onDismissRequest: () -> Unit,
    message: String? = null
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .height(TwoHundredEightyThreeDpClean)
                .width(TwoHundredSeventyEightDpClean),
            shape = RoundedCornerShape(TwentyDpClean),
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colors.white
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = SixteenDpClean,
                        start = SixteenDpClean,
                        end = SixteenDpClean,
                        bottom = TwentyFourDpClean
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /**
                 * Error entity image
                 */
                Image(
                    modifier = Modifier.wrapContentSize(),
                    painter = painterResource(id = R.drawable.ic_error_entity),
                    contentDescription = null,
                )

                /**
                 * Message text
                 */
                Text(
                    text = SomethingGoingWrong,
                    fontSize = EighteenSpClean,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.color212529,
                    textAlign = TextAlign.Center
                )

                /**
                 * Detail information
                 */
                message?.let {
                    Text(
                        modifier = Modifier.padding(top = TenDpClean),
                        text = it,
                        fontSize = SixteenSpClean,
                        color = MaterialTheme.colors.color75212529,
                        textAlign = TextAlign.Center
                    )
                }

                /**
                 * Confirm button
                 */
                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = TwentyFourDpClean),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BtnEnabled,
                    ),
                    shape = RoundedCornerShape(TenDpClean)
                ) {
                    Text(
                        text = MeanIt,
                        style = TextStyle(
                            fontSize = SixteenSpClean,
                            color = Color.White,
                        )
                    )
                }
            }
        }
    }
}