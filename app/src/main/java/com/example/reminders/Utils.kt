package com.example.reminders

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.reminders.database.Reminder

const val DEFAULT_NOTIFICATION_CHANNEL = "default"

fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun makeNotification(context: Context, reminder: Reminder) {
    val builder =
        NotificationCompat.Builder(context, DEFAULT_NOTIFICATION_CHANNEL).apply {
            setContentTitle(reminder.title)
            setContentText(reminder.description)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setSmallIcon(R.drawable.ic_launcher_foreground)
        }
    NotificationManagerCompat.from(context).notify(reminder.id, builder.build())
}
