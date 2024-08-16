package com.example.megalifecompose.ui.main.rating

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.megalifecompose.ui.stop_screens.StopScreenRoute

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    RatingScreen(rememberNavController())
}

@Composable
fun RatingScreen(navController: NavController) {

    StopScreenRoute(navController = navController)

//    val compositionAll by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pers_all))
//    val composition2 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pers2))
//    val composition3 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pers_3_welcom))
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//    )
//    {
//
//        LottieAnimation(
//            composition = compositionAll,
//            iterations = LottieConstants.IterateForever,
//        )
//        LottieAnimation(
//            composition = composition2,
//            iterations = LottieConstants.IterateForever,
//        )
//        LottieAnimation(
//            composition = composition3,
//            iterations = LottieConstants.IterateForever,
//        )
//    }

}