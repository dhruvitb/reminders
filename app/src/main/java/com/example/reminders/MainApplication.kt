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
            resources.getString(R.string.default_notification_channel),
            "Reminders",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = resources.getString(R.string.notification_channel_description)
            setShowBadge(false)
        }
        NotificationManagerCompat.from(this)
            .createNotificationChannel(notificationChannel)
    }
}