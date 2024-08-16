package com.ccink.common.extations

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
    modifier: Modifier = Modifier,
) = this.then(modifier.drawBehind {
    this.drawIntoCanvas {

        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        val spreadPx = spread.toPx()
        val leftPx = (0f - spreadPx) + offsetX.toPx()
        val topPx = (0f - spreadPx) + offsetY.toPx()
        val rightPx = (this.size.width + spreadPx)
        val bottomPx = (this.size.height + spreadPx)

        if (blurRadius != 0.dp) {
            frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
        }

        frameworkPaint.color = color.toArgb()
        it.drawRoundRect(
            left = leftPx, top = topPx, right = rightPx, bottom = bottomPx, radiusX = borderRadius.toPx(), radiusY = borderRadius.toPx(), paint
        )

    }
})

fun NavHostController.navigateWithPopUpTo(
    route: String
) {
    navigate(route = route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}