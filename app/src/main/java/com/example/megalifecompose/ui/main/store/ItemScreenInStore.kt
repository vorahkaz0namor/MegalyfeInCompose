package com.example.megalifecompose.ui.main.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import com.ccink.model.dto.Clothes
import com.ccink.model.dto.FeedItem
import com.ccink.model.dto.Pet
import com.ccink.resources.*

@Composable
fun ItemScreenInStore(
    storeState: StoreState,
    callbackState: CallbackState
) {
            val item = storeState.selectedItem!!

            ConstraintLayout(
                modifier = Modifier
                    .background(item.getItemBackground())
            ) {
                val (img, bck, bal, chsr, sect) = createRefs()

                /**
                 * Back button
                 */
                Box(
                    modifier = Modifier
                        .constrainAs(bck) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .padding(
                            top = TenDp,
                            start = TwentyDp
                        ),
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_store_back),
                        contentDescription = GoBack,
                        modifier = Modifier.clickable { callbackState.onBackButtonClick() }
                    )
                }

                /**
                 * Student balance
                 */
                Box(
                    modifier = Modifier
                        .constrainAs(bal) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                ) { StudentBalanceIndicator(studentBalance = storeState.account.balance) }

                /**
                 * Item image
                 */
                ItemImageOnly(
                    modifier = Modifier
                        .constrainAs(img) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(chsr.top)
                        }
                        .padding(top = TwentyDp)
                        .height(ThreeHundredFiftySixDp)
                    ,
                    item = item,
                    callbackState = callbackState
                )

                /**
                 * Section view chooser
                 */
                Card(
                    shape = RoundedCornerShape(TwentyDpClean),
                    colors = CardDefaults
                        .cardColors(containerColor = MaterialTheme.colors.colorF3ECFB),
                    modifier = Modifier
                        .constrainAs(chsr) {
                            top.linkTo(bal.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(all = TwentyDp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(
                                top = NineDp,
                                start = NineDp,
                                end = NineDp
                            )
                    ) {
                        storeState.viewChooserItems
                            .filter {
                                when (item) {
                                    is Pet -> it.description != CompatibleCharacters
                                    is Clothes -> it.description != SuitableClothes
                                }
                            }
                            .map {
                                Image(
                                    painter = painterResource(id = it.getIcon()),
                                    contentDescription = it.description,
                                    modifier = Modifier
                                        .padding(bottom = NineDp)
                                        .clickable {
                                            callbackState.onChooserItemClick(
                                                it.description,
                                                item::class.java,
                                                item.id
                                            )
                                        }
                                )
                            }
                    }
                }

                /**
                 * Bottom section
                 */
                Box(
                    modifier = Modifier
                        .constrainAs(sect) {
                            start.linkTo(parent.start)
                            top.linkTo(img.bottom)
                        }
                        .padding(top = TwentyFiveDp)
                ) {
                    /**
                     * Injects within ItemDescription() or ItemsRow()
                     */
                    BottomSection {
                        storeState.viewChooserItems.find { it.isSelected }
                            ?.let { chooserItem ->
                                    when (chooserItem.description) {
                                        ItemInformation ->
                                            ItemDescription(
                                                item = item,
                                                itemButtonsState =
                                                callbackState.setItemState(
                                                    item::class.java,
                                                    item.id,
                                                    item.price
                                                ),
                                                onBuyItemClick = callbackState.onBuyItemClick
                                            )
                                        SuitableClothes ->
                                            SuitableClothes(
                                                storeState = storeState,
                                                callbackState = callbackState
                                            )
                                        CompatibleCharacters ->
                                            CompatibleCharacters(
                                                storeState = storeState,
                                                callbackState = callbackState
                                            )
                                    }
                            }
                    }
                }
            }
}

@Composable
fun StudentBalanceIndicator(studentBalance: Int) {
    Box {
        Card(
            shape = RoundedCornerShape(TwentyDpClean),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colors.colorF3ECFB
            ),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(
                    end = TwentyDp,
                    top = TenDp
                )
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = TwentyFourDp,
                    vertical = EightDp
                ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.padding(end = EightDp),
                    painter = painterResource(id = R.drawable.ic_coin),
                    contentDescription = null
                )
                Text(text = studentBalance.toString())
            }
        }
    }
}

@Composable
fun BottomSection(
    content: @Composable () -> Unit
) {
    ConstraintLayout {
        val (head, cnt) = createRefs()

        /**
         * Background head
         */
        Image(
            painter = painterResource(
                id = R.drawable.item_description_background
            ),
            contentScale = ContentScale.FillWidth,
            contentDescription = ItemDescriptionBackground,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(head) {
                    start.linkTo(parent.start)
                    bottom.linkTo(cnt.top)
                }
        )

        /**
         * Section content
         */
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.white)
                .constrainAs(cnt) {
                    start.linkTo(parent.start)
                    top.linkTo(head.bottom)
                }
        ) { content() }
    }
}

@Composable
fun ItemDescription(
    item: FeedItem,
    itemButtonsState: ItemButtonsState,
    onBuyItemClick: (Class<out FeedItem>, Int) -> Unit
) {

    ConstraintLayout(
        modifier = Modifier.padding(horizontal = TwentyDp)
    ) {
        val (name, desc, prc, btn) = createRefs()

        /**
         * Item name
         */
        Text(
            text = item.name,
            fontWeight = FontWeight.Bold,
            fontSize = TwentySp,
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        /**
         * Item price
         */
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(prc) {
                    bottom.linkTo(name.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            Image(
                modifier = Modifier.padding(end = EightDp),
                painter = painterResource(id = R.drawable.ic_coin),
                contentDescription = null
            )
            Text(
                text = item.price.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = TwentyFourSp,
                color = MaterialTheme.colors.colorB066FF
            )
        }

        /**
         * Item description
         */
        Text(
            text = item.description,
            fontSize = SixteenSp,
            lineHeight = TwentyFourSp,
            modifier = Modifier
                .padding(top = EighteenDp)
                .constrainAs(desc) {
                    top.linkTo(name.bottom)
                    start.linkTo(parent.start)
                }
        )

        /**
         * Buy item button (item screen)
         */
        Box(
            modifier = Modifier
                .padding(top = TwentyDp)
                .constrainAs(btn) {
                    top.linkTo(desc.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            StoreItemButton(
                shouldNotAdapt = true,
                isVisible = true,
                caption = itemButtonsState.purchaseButtonCaption,
                isEnabled = itemButtonsState.shouldEnablePurchaseButton,
                onClick = { onBuyItemClick(item::class.java, item.id) }
            )
        }
    }
}

@Composable
fun ItemImageOnly(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillHeight,
    item: FeedItem,
    callbackState: CallbackState
) {
        SubcomposeAsyncImage(
            modifier = modifier,
            contentScale = contentScale,
            model = callbackState.getItemFile(item)
                ?: callbackState.getImageRequest(item)
                ?: ProgressBar(),
            contentDescription = item.name,
            loading = { ProgressBar() },
            onSuccess = { success ->
                callbackState.saveItemImage(
                    item,
                    success.result.drawable
                )
            }
        )
}