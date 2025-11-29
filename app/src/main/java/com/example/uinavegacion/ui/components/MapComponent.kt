package com.example.uinavegacion.ui.components

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun CurrentLocationMap(
    location: Location?,
    modifier: Modifier = Modifier,
    zoom: Float = 16f,
    showMyLocationButton: Boolean = true
) {
    val defaultPosition = LatLng(-33.4489, -70.6693) // Santiago de Chile por defecto
    val currentPosition = location?.let { LatLng(it.latitude, it.longitude) } ?: defaultPosition
    
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentPosition, zoom)
    }

    LaunchedEffect(location) {
        location?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.latitude, it.longitude), zoom
                ), durationMs = 600
            )
        }
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            myLocationButtonEnabled = showMyLocationButton,
            zoomControlsEnabled = false,
            compassEnabled = true
        ),
        properties = MapProperties(
            isMyLocationEnabled = location != null
        )
    ) {
        location?.let {
            Marker(
                state = MarkerState(position = LatLng(it.latitude, it.longitude)),
                title = "Tu ubicación"
            )
        }
    }
}
