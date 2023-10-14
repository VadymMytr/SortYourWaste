package vadym.my.wastesorting.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val lunarGreen = Color(0xFF434443)
val berylGreen = Color(0xFFE1E7C8)
val acapulco = Color(0xFF82B89F)
val como = Color(0xFF54816D)
val stack = Color(0xFF7E897C)
val rangoonGreen = Color(0xFF121610)
val paleLavenderWhite = Color(0xFFEDE9EF)
val osloGray = Color(0xFF969B9C)
val nevada = Color(0xFF5C6C74)
val zorba = Color(0xFFA49C8C)
val darkGray = Color(0xFF282828)

val lightColorPalette = lightColorScheme(
    primary = como,
    onPrimary = berylGreen,
    secondary = acapulco,
    onSecondary = berylGreen,
    background = zorba,
    onBackground = acapulco,
    surface = rangoonGreen,
    onSurface = stack,
    error = nevada,
    onError = zorba,
)

val darkColorPalette = darkColorScheme(
    primary = como,
    onPrimary = berylGreen,
    secondary = lunarGreen,
    onSecondary = berylGreen,
    background = rangoonGreen,
    onBackground = stack,
    surface = zorba,
    onSurface = osloGray,
    error = nevada,
    onError = zorba,
)

@Composable
fun getTextColor() = if (isSystemInDarkTheme()) paleLavenderWhite else rangoonGreen

@Composable
fun getColorPalette() = if (isSystemInDarkTheme()) darkColorPalette else lightColorPalette
