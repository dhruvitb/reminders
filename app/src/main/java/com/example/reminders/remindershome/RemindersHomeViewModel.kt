package com.example.reminders.remindershome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reminders.database.AppDatabase
import com.example.reminders.database.Reminder

class RemindersHomeViewModel(database: AppDatabase) : ViewModel() {
    val allReminders = database.reminderDao.getAll()

    private val _navigateToNewReminder = MutableLiveData<Reminder>()
    val navigateToNewReminder: LiveData<Reminder>
        get() = _navigateToNewReminder

    fun addNewReminder() {
        _navigateToNewReminder.value = Reminder(0, "", "")
    }

    fun finishNewReminderNavigation() {
        _navigateToNewReminder.value = null
    }
}

class RemindersHomeViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(RemindersHomeViewModel::class.java)) {
            return RemindersHomeViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
