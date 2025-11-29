package com.example.uinavegacion.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class GeocodingService(private val context: Context) {
    private val geocoder = Geocoder(context)

    suspend fun getAddressFromLocation(location: Location): String? = withContext(Dispatchers.IO) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Android 13+ usa callback
                suspendCancellableCoroutine { continuation ->
                    geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    ) { addresses ->
                        continuation.resume(addresses.firstOrNull()?.formatAddress())
                    }
                }
            } else {
                // Versiones anteriores usan API bloqueante
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                addresses?.firstOrNull()?.formatAddress()
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun Address.formatAddress(): String {
        return when {
            thoroughfare != null && subThoroughfare != null -> 
                "$thoroughfare $subThoroughfare, $locality"
            thoroughfare != null -> 
                "$thoroughfare, $locality"
            locality != null -> 
                locality
            else -> 
                "${"%.4f".format(latitude)}, ${"%.4f".format(longitude)}"
        }
    }
}
