package com.example.reminders.reminderdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.reminders.database.AppDatabase
import com.example.reminders.database.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReminderDetailViewModel(
    private val database: AppDatabase, private val reminderId: Int?
) : ViewModel() {
    var reminder: Reminder? = null

    init {
        reminderId?.let {
            // TODO make sure this use of threads is correct
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    reminder = database.reminderDao.getReminder(reminderId)
                }
            }
        }
    }
}

class RemindersDetailViewModelFactory(
    private val database: AppDatabase, private val reminderId: Int?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(ReminderDetailViewModel::class.java)) {
            return ReminderDetailViewModel(database, reminderId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
