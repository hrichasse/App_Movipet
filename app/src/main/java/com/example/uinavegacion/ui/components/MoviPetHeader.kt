package com.example.uinavegacion.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetTeal
import com.example.uinavegacion.ui.theme.MoviPetWhite

/**
 * Header reutilizable con logo MoviPet, título y botón de navegación.
 * 
 * @param title Título de la pantalla
 * @param onBackClick Acción al presionar el botón back (null para ocultarlo)
 * @param onHomeClick Acción al presionar el botón home (null para ocultarlo)
 */
@Composable
fun MoviPetHeader(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onHomeClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MoviPetOrange)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón Back (si existe)
        if (onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MoviPetWhite
                )
            }
        } else {
            Spacer(Modifier.size(48.dp))
        }

        // Logo + Título centrados
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Surface(
                modifier = Modifier.size(32.dp),
                color = MoviPetTeal,
                shape = CircleShape
            ) {
                Icon(
                    Icons.Filled.Pets,
                    contentDescription = "Logo MoviPet",
                    tint = MoviPetWhite,
                    modifier = Modifier.padding(6.dp)
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MoviPetWhite
            )
        }

        // Botón Home (si existe) o espaciador
        if (onHomeClick != null) {
            IconButton(onClick = onHomeClick) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Ir al inicio",
                    tint = MoviPetWhite
                )
            }
        } else {
            Spacer(Modifier.size(48.dp))
        }
    }
}
