package com.example.uinavegacion.ui.components

import androidx.compose.material.icons.Icons // Conjunto de íconos Material
import androidx.compose.material.icons.filled.Home // Ícono Home
import androidx.compose.material.icons.filled.AccountCircle // Ícono Login
import androidx.compose.material.icons.filled.Menu // Ícono hamburguesa
import androidx.compose.material.icons.filled.MoreVert // Ícono 3 puntitos (overflow)
import androidx.compose.material.icons.filled.Person // Ícono Registro
import androidx.compose.material3.CenterAlignedTopAppBar // TopAppBar centrada
import androidx.compose.material3.DropdownMenu // Menú desplegable
import androidx.compose.material3.DropdownMenuItem // Opción del menú
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon // Para mostrar íconos
import androidx.compose.material3.IconButton // Botones con ícono
import androidx.compose.material3.MaterialTheme // Tema Material
import androidx.compose.material3.Text // Texto
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.* // remember / mutableStateOf
import androidx.compose.ui.text.style.TextOverflow

<<<<<<< HEAD
// --- imports agregados para logo y colores de marca ---
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.uinavegacion.R
import com.example.uinavegacion.ui.theme.MoviPetOrange
import com.example.uinavegacion.ui.theme.MoviPetWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable // Composable reutilizable: barra superior
fun AppTopBar(
    onOpenDrawer: () -> Unit = {}, // Abre el drawer (hamburguesa)  // ← default agregado
    onHome: () -> Unit = {},       // Navega a Home                   // ← default agregado
    onLogin: () -> Unit = {},      // Navega a Login                  // ← default agregado
    onRegister: () -> Unit = {}    // Navega a Registro               // ← default agregado
=======
@OptIn(ExperimentalMaterial3Api::class)
@Composable // Composable reutilizable: barra superior
fun AppTopBar(
    onOpenDrawer: () -> Unit, // Abre el drawer (hamburguesa)
    onHome: () -> Unit,       // Navega a Home
    onLogin: () -> Unit,      // Navega a Login
    onRegister: () -> Unit    // Navega a Registro
>>>>>>> 7db42d458292e54c947495fafecdf6e3159c32d1
) {
    //lo que hace es crear una variable de estado recordada que le dice a la interfaz
    // si el menú desplegable de 3 puntitos debe estar visible (true) o oculto (false).
    var showMenu by remember { mutableStateOf(false) } // Estado del menú overflow

    CenterAlignedTopAppBar( // Barra alineada al centro
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
<<<<<<< HEAD
            containerColor = MoviPetOrange,                 // ← naranjo de marca
            titleContentColor = MoviPetWhite,               // ← texto blanco
            navigationIconContentColor = MoviPetWhite,      // ← íconos blancos
            actionIconContentColor = MoviPetWhite           // ← íconos blancos
        ),
        title = { // Slot del título
            // Si existe el logo en drawable, se muestra; si no, se mantiene el título de texto.
            val logoOk = remember {
                try {
                    painterResource(id = R.drawable.ic_logo_movipet)
                    true
                } catch (_: Exception) {
                    false
                }
            }
            if (logoOk) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo_movipet),
                    contentDescription = null
                )
            } else {
                Text(
                    text = "Demo Navegación Compose", // Título visible
                    style = MaterialTheme.typography.titleLarge, // Estilo grande
                    maxLines = 1,              // asegura una sola línea Int.MAX_VALUE   // permite varias líneas
                    overflow = TextOverflow.Ellipsis // agrega "..." si no cabe
                )
            }
=======
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = { // Slot del título
            Text(
                text = "Demo Navegación Compose", // Título visible
                style = MaterialTheme.typography.titleLarge, // Estilo grande
                maxLines = 1,              // asegura una sola línea Int.MAX_VALUE   // permite varias líneas
                overflow = TextOverflow.Ellipsis // agrega "..." si no cabe

            )
>>>>>>> 7db42d458292e54c947495fafecdf6e3159c32d1
        },
        navigationIcon = { // Ícono a la izquierda (hamburguesa)
            IconButton(onClick = onOpenDrawer) { // Al presionar, abre drawer
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menú") // Ícono
            }
        },
        actions = { // Acciones a la derecha (íconos + overflow)
            IconButton(onClick = onHome) { // Ir a Home
                Icon(Icons.Filled.Home, contentDescription = "Home") // Ícono Home
            }
            IconButton(onClick = onLogin) { // Ir a Login
                Icon(Icons.Filled.AccountCircle, contentDescription = "Login") // Ícono Login
            }
            IconButton(onClick = onRegister) { // Ir a Registro
                Icon(Icons.Filled.Person, contentDescription = "Registro") // Ícono Registro
            }
            IconButton(onClick = { showMenu = true }) { // Abre menú overflow
                Icon(Icons.Filled.MoreVert, contentDescription = "Más") // Ícono 3 puntitos
            }
            DropdownMenu(
                expanded = showMenu, // Si está abierto
                onDismissRequest = { showMenu = false } // Cierra al tocar fuera
            ) {
                DropdownMenuItem( // Opción Home
                    text = { Text("Home") }, // Texto opción
                    onClick = { showMenu = false; onHome() } // Navega y cierra
                )
                DropdownMenuItem( // Opción Login
                    text = { Text("Login") },
                    onClick = { showMenu = false; onLogin() }
                )
                DropdownMenuItem( // Opción Registro
                    text = { Text("Registro") },
                    onClick = { showMenu = false; onRegister() }
                )
            }
        }
    )
<<<<<<< HEAD
}
=======
}
>>>>>>> 7db42d458292e54c947495fafecdf6e3159c32d1
