package com.example.uinavegacion.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MoviPetOrange,
    secondary = MoviPetTeal,
    tertiary = MoviPetTeal,
    background = MoviPetBlack,
    surface = MoviPetDarkGray,
    onPrimary = MoviPetWhite,
    onSecondary = MoviPetWhite,
    onTertiary = MoviPetWhite,
    onBackground = MoviPetWhite,
    onSurface = MoviPetWhite
)

private val LightColorScheme = lightColorScheme(
    primary = MoviPetOrange,
    secondary = MoviPetTeal,
    tertiary = MoviPetTeal,
    background = MoviPetWhite,
    surface = MoviPetWhite,
    onPrimary = MoviPetWhite,
    onSecondary = MoviPetWhite,
    onTertiary = MoviPetWhite,
    onBackground = MoviPetBlack,
    onSurface = MoviPetBlack
)

@Composable
fun UINavegacionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
