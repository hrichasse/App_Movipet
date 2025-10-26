package com.example.uinavegacion.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uinavegacion.data.ThemePreferences
import com.example.uinavegacion.ui.theme.MoviPetOrange
import kotlinx.coroutines.launch

@Composable
fun ThemeToggleMenuItem() {
    val context = LocalContext.current
    val themePreferences = remember { ThemePreferences(context) }
    val isDarkTheme by themePreferences.isDarkTheme.collectAsState(initial = false)
    val scope = rememberCoroutineScope()
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.DarkMode,
            contentDescription = "Modo oscuro",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = "Modo oscuro",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        
        Switch(
            checked = isDarkTheme,
            onCheckedChange = {
                scope.launch {
                    themePreferences.toggleTheme()
                }
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MoviPetOrange,
                checkedTrackColor = MoviPetOrange.copy(alpha = 0.5f)
            )
        )
    }
}
