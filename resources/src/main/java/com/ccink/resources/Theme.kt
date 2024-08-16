package com.ccink.resources

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

@Stable
data class BusinessColors(
    val primary: Color = Color.White,
    val secondary: Color = Color(0xFFA8A9BA),
    val colorB066FF: Color = Color(0xFFB066FF),
    val color9DB2CE: Color = Color(0xFF9DB2CE),
    val colorD4AAFF: Color = Color(0xFFD4AAFF),
    val colorA7A3FF: Color = Color(0xFFA7A3FF),
    val colorA5B4FC: Color = Color(0xFFA5B4FC),
    val colorA7F3D0: Color = Color(0xFFA7F3D0),
    val colorFDBA74: Color = Color(0xFFFDBA74),
    val colorCED4DA: Color = Color(0xFFCED4DA),
    val colorF0EFFF: Color = Color(0xFFF0EFFF),
    val color545454: Color = Color(0xFF545454),
    val color212529: Color = Color(0xFF212529),
    val color75212529: Color = Color(0x75212529),
    val color9B9B9B: Color = Color(0xFF9B9B9B),
    val colorF3ECFB: Color = Color(0xFFF3ECFB),
    val colorE5A1BB: Color = Color(0xFFE5A1BB),
    val colorFF4E64: Color = Color(0xFFFF4E64),
    val color1F18274B: Color = Color(0x1F18274B),
    val color1418274B: Color = Color(0x1418274B),
    val color5BCECB: Color = Color(0xFF5BCECB),
    val colorFFC25B: Color = Color(0xFFFFC25B),
    val color5FA038: Color = Color(0xFF5FA038),

    val color292E37: Color = Color(0xFF292E37),
    val color2E2E2E: Color = Color(0xFF2E2E2E),
    val color20BF63: Color = Color(0xFF20BF63),
    val colorF5F5F5: Color = Color(0xFFF5F5F5),
    val colorF61E2E: Color = Color(0xFFF61E2E),
    val color920448: Color = Color(0xFF920448),
    val color26263B: Color = Color(0xFF26263B),
    val colorC885A1: Color = Color(0xFFC885A1),
    val colorDBDBE2: Color = Color(0xFFDBDBE2),
    val colorFFD482: Color = Color(0xFFFFD482),
    val colorFF377F: Color = Color(0xFFFF377F),
    val color2D6190: Color = Color(0xFF2D6190),
    val colorB3B4C6: Color = Color(0xFFB3B4C6),
    val color767680: Color = Color(0xFF767680),
    val color23B07D: Color = Color(0xFF23B07D),
    val color2BCBBA: Color = Color(0xFF2BCBBA),
    val colorA8A9BA: Color = Color(0xFFA8A9BA),
    val color7CE5E8: Color = Color(0xFF7CE5E8),
    val transparent: Color = Color.Transparent,
    val white: Color = Color.White,


    val color930C40: Color = Color(0xFF930C40),
    val colorE38EA2: Color = Color(0xFFE38EA2),

    val linearGradientRed: Brush = Brush.linearGradient(
        0.0f to colorF61E2E,
        500.0f to color920448,
        start = Offset.Zero,
        end = Offset.Infinite,
    ),
)

@Stable
class BusinessStyle(
    val s20h24w900: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Black,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),

    val s14h17w400: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 17.sp,
    ),

    val s14h17w500: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 17.sp,
    ),

    val s12w400: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
    ),

    val s16w700: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
    ),

    val s16w400h19: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 19.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
    ),

    val s12h14w400: TextStyle = TextStyle(
        fontSize = 12.sp,
        lineHeight = 14.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
    ),

    val s24h29w600: TextStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 29.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black
    ),

    val s13h16w400: TextStyle = TextStyle(
        fontSize = 13.sp,
        lineHeight = 16.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
    ),

    val s17h20w600: TextStyle = TextStyle(
        fontSize = 17.sp,
        lineHeight = 20.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
    ),

    val s11h13w600: TextStyle = TextStyle(
        fontSize = 11.sp,
        lineHeight = 13.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
    ),


    )

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

private val LightColors = BusinessColors()
private val Style = BusinessStyle()
private val LocalColors = staticCompositionLocalOf { LightColors }
private val LocalStyle = staticCompositionLocalOf { Style }

@Composable
fun MegaLifeComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme

            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
        }
    }

    CompositionLocalProvider(LocalColors provides LightColors) {
        CompositionLocalProvider(LocalStyle provides Style) {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography,
                content = content
            )
        }
    }
}

val MaterialTheme.colors: BusinessColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current
val MaterialTheme.styles: BusinessStyle
    @Composable
    @ReadOnlyComposable
    get() = LocalStyle.current

object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}