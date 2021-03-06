package com.example.reminders

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.example.reminders.database.Reminder

fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun makeNotification(context: Context, reminder: Reminder) {
    val intent = NavDeepLinkBuilder(context).apply {
        setGraph(R.navigation.nav_graph)
        setDestination(R.id.reminderDetail)
        setArguments(bundleOf(Pair("reminder", reminder)))
    }.createPendingIntent()
    val builder =
        NotificationCompat.Builder(
            context,
            context.getString(R.string.default_notification_channel)
        ).apply {
            setContentTitle(reminder.title)
            setContentText(reminder.description)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setOngoing(true)
            setContentIntent(intent)
            setGroup(context.getString(R.string.default_notification_group))
            setVibrate(LongArray(0))
        }
    NotificationManagerCompat.from(context).notify(reminder.id, builder.build())
}
