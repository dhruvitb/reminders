package com.example.reminders

import android.app.Activity
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.reminders.database.Reminder


fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

@RequiresApi(Build.VERSION_CODES.O)
fun makeNotification(context: Context, reminder: Reminder) {
    val builder =
        NotificationCompat.Builder(context, NotificationChannel.DEFAULT_CHANNEL_ID).apply {
            setContentTitle(reminder.title)
            setContentText(reminder.description)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }
    val notification = builder.build()
    NotificationManagerCompat.from(context).notify(reminder.id, notification)
}
