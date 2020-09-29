package com.example.reminders

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationManagerCompat
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val notificationChannel = NotificationChannel(
            DEFAULT_NOTIFICATION_CHANNEL, "Reminders", NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Default Notification Category"
        }
        NotificationManagerCompat.from(this)
            .createNotificationChannel(notificationChannel)
    }
}