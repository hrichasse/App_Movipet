package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uinavegacion.navigation.Route
import com.example.uinavegacion.notification.NotificationHelper
import com.example.uinavegacion.ui.components.MoviPetHeader
import com.example.uinavegacion.ui.theme.MoviPetLightGray
import com.example.uinavegacion.ui.theme.MoviPetOrange

@Composable
fun PaymentMethodScreen(navController: NavController) {
    val context = LocalContext.current
    
    val onPaymentSelected = {
        NotificationHelper.notifyTripCompleted(context)
        navController.navigate(Route.Rating.path)
    }
    
    Column(Modifier.fillMaxSize().background(MoviPetLightGray)) {
        MoviPetHeader(
            title = "Método de pago",
            onBackClick = { navController.popBackStack() }
        )

        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Selecciona cómo quieres pagar", fontSize = 16.sp)
            Spacer(Modifier.height(16.dp))

            Button(onClick = onPaymentSelected, modifier = Modifier.fillMaxWidth().height(50.dp)) { Text("Tarjeta Débito") }
            Spacer(Modifier.height(12.dp))
            Button(onClick = onPaymentSelected, modifier = Modifier.fillMaxWidth().height(50.dp)) { Text("Tarjeta Crédito") }
            Spacer(Modifier.height(12.dp))
            OutlinedButton(onClick = onPaymentSelected, modifier = Modifier.fillMaxWidth().height(50.dp)) { Text("Efectivo") }
        }

        Box(Modifier.fillMaxWidth().height(8.dp).background(MoviPetOrange))
    }
}
