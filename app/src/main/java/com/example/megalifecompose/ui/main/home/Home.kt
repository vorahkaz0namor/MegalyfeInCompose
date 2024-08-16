import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ccink.resources.R
import com.ccink.resources.colors
import com.example.megalifecompose.flowBus.HomeEvent
import com.example.megalifecompose.ui.main.home.HandlingLoadState
import com.example.megalifecompose.ui.main.home.HomeData
import com.example.megalifecompose.ui.main.home.HomeViewModel
import com.example.megalifecompose.ui.main.home.getAnimationFile
import com.example.megalifecompose.ui.main.store.ProgressBar
import com.google.android.material.math.MathUtils.lerp
import kotlin.math.absoluteValue


@Preview(showBackground = true)
@Composable
fun ShowHomeScreen() {
    Home()
}

@Composable
fun HomeRoute(viewModel: HomeViewModel = viewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    state?.HandlingLoadState(onDismissRequest = {

        viewModel.sendEvent(HomeEvent.RefreshHome)

    }) { homeData ->
        Home(homeData)
    }
}

@Composable
private fun Home(homeData: HomeData = HomeData()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Header(studentBalance = homeData.account.balance)

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            PagerItem(homeData)

        }

    }
}

@Composable
fun Header(
    studentBalance: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp),
    ) {
        Box(modifier = Modifier.fillMaxWidth(0.5f)) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_settings
                ),
                contentDescription = null,
                tint = MaterialTheme.colors.color9DB2CE
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colors.colorF3ECFB
                )
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 8.dp
                    ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.padding(end = 8.dp),
                        painter = painterResource(id = R.drawable.ic_coin),
                        contentDescription = null
                    )
                    Text(text = studentBalance.toString())
                }
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PagerItem(homeData: HomeData) {
    val pets = homeData.accountPets
    val pagerState = rememberPagerState(
        initialPage = pets.size / 2,
        pageCount = { pets.size }
    )

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fill,
        contentPadding = PaddingValues(top = 36.dp, start = 49.dp, end = 49.dp, bottom = 70.dp),
    ) { page ->

        val pageOffsetL =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {

                if (page == pagerState.currentPage + 1) {
                    rotationZ = lerp(
                        6f, 0f, 1f - pageOffsetL.coerceIn(0f, 1f)
                    )
                }

                if (page == pagerState.currentPage - 1) {
                    rotationZ = lerp(
                        -6f, 0f, 1f - pageOffsetL.coerceIn(0f, 1f)
                    )
                }

                scaleX = lerp(
                    0.80f, 1f, 1f - pageOffsetL.coerceIn(0f, 1f)
                )

                scaleY = lerp(
                    0.80f, 1f, 1f - pageOffsetL.coerceIn(0f, 1f)
                )

            }) {

            val (card, img, wave) = createRefs()

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp)
                    .constrainAs(card) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                shape = RoundedCornerShape(35.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.colorE5A1BB)
            ) {

//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    Image(painter = painterResource(id = R.drawable.ic_add), contentDescription = "add")
//                }

            }

            Box(modifier = Modifier.constrainAs(wave) {
                bottom.linkTo(card.bottom)
                start.linkTo(card.start)
                end.linkTo(card.end)
                width = Dimension.fillToConstraints
            }, contentAlignment = Alignment.Center) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 1.dp),
                    painter = painterResource(id = R.drawable.bg_wave),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = pets[page].name,
                    style = TextStyle(
                        fontSize = 26.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFFFF),
                    )
                )

            }

//            val compositionAll by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pers1))
            homeData.getAnimationFile(pets[page].id)?.let {
                val compositionAll by rememberLottieComposition(
                    spec = LottieCompositionSpec.JsonString(it)
                )

                LottieAnimation(
                    modifier = Modifier
                        .constrainAs(img) {
                            bottom.linkTo(wave.top)
                            start.linkTo(wave.start)
                            end.linkTo(wave.end)
                            top.linkTo(parent.top)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        },
                    composition = compositionAll,
                    iterations = LottieConstants.IterateForever,
                    isPlaying = page == pagerState.currentPage
                )

            } ?: ProgressBar()

        }

    }
}

//@Composable
//@Preview
//private fun Test() {
//
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//
//        val compositionAll by rememberLottieComposition(
//            LottieCompositionSpec.RawRes(
//                R.raw.marvel
//            )
//        )
//
//        LottieAnimation(
//            modifier = Modifier,
//            composition = compositionAll,
//            iterations = LottieConstants.IterateForever,
//        )
//
//    }
//
//}