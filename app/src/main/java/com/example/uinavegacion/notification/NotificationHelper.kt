package com.example.uinavegacion.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.uinavegacion.MainActivity

object NotificationHelper {
    private const val CHANNEL_ID = "movipet_trips"
    private const val CHANNEL_NAME = "Viajes MoviPet"
    private const val CHANNEL_DESC = "Notificaciones de estado de viajes"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESC
                enableVibration(true)
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendTripNotification(context: Context, title: String, message: String, notificationId: Int = 1) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Cambiar por icono personalizado
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            // Verificar permisos de notificación (Android 13+)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == 
                android.content.pm.PackageManager.PERMISSION_GRANTED) {
                notify(notificationId, builder.build())
            }
        }
    }

    // Notificaciones específicas del flujo de viaje
    fun notifyDriverAssigned(context: Context, driverName: String) {
        sendTripNotification(
            context,
            "Conductor asignado",
            "$driverName está en camino a recogerte"
        )
    }

    fun notifyDriverNearby(context: Context) {
        sendTripNotification(
            context,
            "Conductor cerca",
            "Tu conductor está a menos de 2 minutos"
        )
    }

    fun notifyTripStarted(context: Context) {
        sendTripNotification(
            context,
            "Viaje iniciado",
            "Tu mascota está en camino a la veterinaria"
        )
    }

    fun notifyTripCompleted(context: Context) {
        sendTripNotification(
            context,
            "Viaje completado",
            "Has llegado a tu destino. Por favor valora el servicio"
        )
    }
}
