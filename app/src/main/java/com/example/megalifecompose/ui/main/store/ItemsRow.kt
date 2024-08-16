/**
 * Would be used both in ItemScreen and StoreMainFragment
 */
package com.example.megalifecompose.ui.main.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ccink.common.extations.shadow
import com.ccink.model.dto.FeedItem
import com.ccink.model.dto.Pet
import com.ccink.resources.*

@Preview(
    showBackground = true,
    device = "spec:width=309dp,height=477dp"
)
@Composable
private fun PreviewFromStoreMainScreen() {
    PreviewStoreMainScreen()
}

@Composable
fun RowSection(
    title: String,
    items: List<FeedItem>,
    storeState: StoreState,
    callbackState: CallbackState,
    shouldDisplayItemsCounter: Boolean = false
) {
    val isMainRow = title == CharactersCollection
    val activeItemId = rememberSaveable {
        mutableIntStateOf(storeState.activePetId)
    }
    val activeItemIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    val listState = rememberForeverLazyListState(
        onDisposeKey = storeState.account,
        positionKey = title
    )
    val firstVisibleItemIndex by remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }
    val hasAllowedScrollOffset by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset < ThreeHundredSixteenDp.value
        }
    }
    val isActiveItem: (Int) -> Boolean = { index ->
        index == firstVisibleItemIndex && hasAllowedScrollOffset
                || index == firstVisibleItemIndex + 1 && !hasAllowedScrollOffset
    }

    if (isMainRow) {
        callbackState.setActivePetId(activeItemId.intValue)
    }

    Column {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = NineteenDp,
                    end = TwentyOneDp,
                    bottom = EighteenDp
                )
        ) {
            val (text, ind) = createRefs()

            /**
             * Section title
             */
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = TwentySp,
                modifier = Modifier
                    .constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .constrainAs(ind) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        visibility =
                            if (shouldDisplayItemsCounter)
                                Visibility.Visible
                            else
                                Visibility.Gone
                    }
            ) {
                /**
                 * Presented item number
                 */
                Text(
                    text =
                    when {
                        items.isNotEmpty() &&
                        items.first() is Pet -> "${activeItemIndex.intValue}/"
                        isMainRow -> "-/"
                        else -> ""
                    },
                    fontSize = TwentySp,
                    fontWeight = FontWeight.Bold
                )

                /**
                 * Total items number
                 */
                Text(
                    text = items.size.toString(),
                    fontSize = SixteenSp
                )
            }
        }

        /**
         * Items list
         */
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement
                .spacedBy(if (isMainRow) ZeroDp else TwentyDp),
            contentPadding = PaddingValues(horizontal =
                if (isMainRow) RowPaddingDp else TwentyDp
            )
        ) {
            itemsIndexed(items = items) { index, item ->
                val displayAnimation = remember {
                    mutableStateOf(false)
                }
                val animationFile = remember {
                    mutableStateOf<String?>(null)
                }

                if (isMainRow && isActiveItem(index)) {
                    activeItemId.intValue = item.id
                    activeItemIndex.intValue = index + 1
                }

                if (isMainRow) {
                    LaunchedEffect(key1 = item, key2 = activeItemId.intValue) {
                        displayAnimation.value = item.id == activeItemId.intValue
                        animationFile.value =
                            if (displayAnimation.value)
                                storeState.getAnimationFile(item.id)
                            else
                                null
                    }
                }

                ItemCard(
                    modifier = Modifier.scale(
                        if (isMainRow && !isActiveItem(index)) 0.8F else 1F
                    ),
                    isMainRow = isMainRow,
                    displayAnimation = displayAnimation.value,
                    animationFile = animationFile.value,
                    item = item,
                    callbackState = callbackState,
                    itemButtonsState =
                    callbackState.setItemState(
                        item::class.java,
                        item.id,
                        item.price
                    ),
                    putOnClothes = storeState.addActivePetIdToPutOnClothes(callbackState.putOnClothes)
                )
            }
        }
    }
}

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    isMainRow: Boolean,
    displayAnimation: Boolean,
    animationFile: String?,
    item: FeedItem,
    callbackState: CallbackState,
    itemButtonsState: ItemButtonsState,
    putOnClothes: (Int?) -> Unit
) {
    Column {
        /**
         * Item card
         */
        ElevatedCard(
            shape = if (isMainRow) CardDefaults.elevatedShape else CardDefaults.shape,
            elevation =
            if (isMainRow)
                CardDefaults.elevatedCardElevation(EightDpClean)
            else
                CardDefaults.elevatedCardElevation(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = item.getItemBackground()
            ),
            modifier = modifier
                .height(if (isMainRow) ThreeHundredSixteenDp else OneHundredThirtyEightDp)
                .width(if (isMainRow) TwoHundredEighteenDp else OneHundredTwentyEightDp)
                .let {
                    if (isMainRow)
                        it.shadow(
                            color = item.getItemBackground(),
                            blurRadius = FourteenDpClean,
                            borderRadius = ThirteenDpClean
                        )
                    else
                        it
                }
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (img, nameWithBtn, botRowBtn) = createRefs()
                val itemFontSize =
                    if (isMainRow) SixteenSp else TwelveSp

                /**
                 * Item image
                 */
                Box(
                    modifier = Modifier
                        .constrainAs(img) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(nameWithBtn.top)
                            height = Dimension.fillToConstraints
                        }
                        .padding(
                            top = SixteenDp,
                            bottom = EightDp
                        )
//                        .height(
//                            if (isMainRow)
//                                TwoHundredFiftyTwoDp
//                            else
//                                NinetyEightDp
//                        )
                ) {
                    if (displayAnimation) {
                        val composition by rememberLottieComposition(
                            spec = LottieCompositionSpec.JsonString(
                                animationFile ?: ""
                            )
                        )

                        composition?.let {
                            LottieAnimation(
                                composition = it,
                                iterations = LottieConstants.IterateForever,
                                isPlaying = true
                            )
                        } ?: ProgressBar()
                    } else
                        ItemImageOnly(
                            item = item,
                            callbackState = callbackState
                        )
                }

                ConstraintLayout(
                    modifier = Modifier
                        .constrainAs(nameWithBtn) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.matchParent
                        }
                        .padding(horizontal = TwentyDp)
                        .padding(bottom = FourDp)
                ) {
                    /**
                     * Item name
                     */
                    Text(
                        text = item.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = itemFontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .constrainAs(createRef()) {
                                start.linkTo(parent.start)
                                if (!isMainRow) end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )

                    /**
                     * Item details button (main row)
                     */
                    Image(
                        painter = painterResource(R.drawable.ic_go_to_full_item_info),
                        contentDescription = Details,
                        modifier = Modifier
                            .size(ThirtyTwoDp)
                            .constrainAs(createRef()) {
                                end.linkTo(parent.end)
                                visibility =
                                    if (isMainRow)
                                        Visibility.Visible
                                    else
                                        Visibility.Gone
                            }
                            .clickable {
                                callbackState.onSelectedItemClick(
                                    item::class.java,
                                    item.id
                                )
                            }
                    )
                }

                /**
                 * Item details button (bottom row)
                 */
                Image(
                    painter = painterResource(R.drawable.ic_go_to_full_item_info),
                    contentDescription = Details,
                    modifier = Modifier
                        .size(ThirtyDp)
                        .constrainAs(botRowBtn) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            visibility =
                                if (!isMainRow)
                                    Visibility.Visible
                                else
                                    Visibility.Gone
                        }
                        .padding(
                            end = EightDp,
                            top = EightDp
                        )
                        .clickable {
                            callbackState.onSelectedItemClick(
                                item::class.java,
                                item.id
                            )
                        }
                )
            }
        }

        /**
         * Buy item button (items list)
         */
        StoreItemButton(
            shouldNotAdapt = callbackState.isItemScreen() && isMainRow,
            isVisible = !isMainRow && itemButtonsState.purchaseButtonVisibility,
            caption = itemButtonsState.purchaseButtonCaption,
            isEnabled = itemButtonsState.shouldEnablePurchaseButton
        ) { callbackState.onBuyItemClick(item::class.java, item.id) }

        /**
         * Wear on clothes button
         */
        StoreItemButton(
            shouldNotAdapt = callbackState.isItemScreen() && isMainRow,
            isVisible = !isMainRow && itemButtonsState.putOnButtonVisibility,
            caption = itemButtonsState.putOnButtonCaption,
            isEnabled = itemButtonsState.shouldEnablePutOnButton,
        ) { putOnClothes(item.id) }
    }
}

@Composable
fun StoreItemButton(
    shouldNotAdapt: Boolean,
    isVisible: Boolean,
    caption: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
        ConstraintLayout {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .let {
                        if (shouldNotAdapt)
                            it.fillMaxWidth()
                        else
                            it
                                .width(OneHundredTwentyEightDp)
                                .padding(top = TenDp)
                    }
                    .height(if (shouldNotAdapt) FiftySixDp else FortyEightDp)
                    .constrainAs(createRef()) {
                        visibility = if (isVisible)
                            Visibility.Visible
                        else
                            Visibility.Gone
                    },
                contentPadding =
                    PaddingValues(
                        vertical = EightDpClean,
                        horizontal = TwelveDpClean
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BtnEnabled,
                    disabledContainerColor = BtnNotEnabled
                ),
                shape = RoundedCornerShape(EightDpClean),
                enabled = isEnabled
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.padding(end = EightDp),
                        painter = painterResource(id = R.drawable.ic_coin),
                        contentDescription = null
                    )

                    Text(
                        text = caption,
                        style = TextStyle(
                            fontSize = if (shouldNotAdapt) TwelveSpClean else TwelveSp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.white,
                        )
                    )
                }
            }
        }
}