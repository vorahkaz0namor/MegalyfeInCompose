package com.ccink.resources

import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
private const val INITIAL_HEIGHT = 1515
private const val INITIAL_DENSITY = 1.75F
private const val MYSTERIOUS = -64
private const val INITIAL_ALLOWED_HEIGHT = INITIAL_HEIGHT / INITIAL_DENSITY
val adaptationRatio =
    (metrics.heightPixels / metrics.density).let {
        (it + if (it < INITIAL_ALLOWED_HEIGHT) MYSTERIOUS else 0)
            .div(INITIAL_ALLOWED_HEIGHT)
    }

val ZeroDp = 0.dp
val FourDpClean = 4.dp
val FourDp = FourDpClean * adaptationRatio
val EightDpClean = 8.dp
val EightDp = EightDpClean * adaptationRatio
val NineDp = 9.dp * adaptationRatio
val TenDpClean = 10.dp
val TenDp = TenDpClean * adaptationRatio
val TwelveDpClean = 12.dp
val TwelveSpClean = 12.sp
val TwelveSp = TwelveSpClean * adaptationRatio
val ThirteenDpClean = 13.dp
val FourteenDpClean = 14.dp
val SixteenSpClean = 16.sp
val SixteenSp = SixteenSpClean * adaptationRatio
val SixteenDpClean = 16.dp
val SixteenDp = SixteenDpClean * adaptationRatio
val EighteenDp = 18.dp * adaptationRatio
val EighteenSpClean = 18.sp
val NineteenDp = 19.dp * adaptationRatio
val TwentyDpClean = 20.dp
val TwentyDp = TwentyDpClean * adaptationRatio
val TwentySp = 20.sp * adaptationRatio
val TwentyOneDp = 21.dp * adaptationRatio
val TwentyFourSpClean = 24.sp
val TwentyFourSp = TwentyFourSpClean * adaptationRatio
val TwentyFourDpClean = 24.dp
val TwentyFourDp = TwentyFourDpClean * adaptationRatio
val TwentyFiveDp = 25.dp * adaptationRatio
val ThirtyDpClean = 30.dp
val ThirtyDp = ThirtyDpClean * adaptationRatio
val ThirtyTwoDp = 32.dp * adaptationRatio
val ThirtySixDp = 36.dp * adaptationRatio
val FortyThreeDp = 43.dp * adaptationRatio
val FortyEightDpClean = 48.dp
val FortyEightDp = FortyEightDpClean * adaptationRatio
val FiftySixDp = 56.dp * adaptationRatio
val SeventyTwoDp = 72.dp * adaptationRatio
val SeventySixDpClean = 76.dp
val NinetyEightDpClean = 98.dp
val OneHundredTwentyEightDpClean = 128.dp
val OneHundredTwentyEightDp = OneHundredTwentyEightDpClean * adaptationRatio
val OneHundredThirtyEightDpClean = 138.dp
val OneHundredThirtyEightDp = OneHundredThirtyEightDpClean * adaptationRatio
val OneHundredFiftyThreeDpClean = 153.dp
val OneHundredSeventyEightDpClean = 178.dp
val TwoHundredSixteenDpClean = 216.dp
val TwoHundredEighteenDpClean = 218.dp
val TwoHundredEighteenDp = TwoHundredEighteenDpClean * adaptationRatio
val TwoHundredSeventyEightDpClean = 278.dp
val TwoHundredEightyThreeDpClean = 283.dp
val ThreeHundredSixteenDp = 316.dp * adaptationRatio
val ThreeHundredFiftySixDp = 356.dp * adaptationRatio
val RowPaddingDp =
    (metrics.widthPixels
        .div(metrics.density))
        .dp
        .minus(TwoHundredEighteenDp)
        .div(2)