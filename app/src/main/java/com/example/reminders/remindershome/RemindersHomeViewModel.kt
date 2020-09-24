package com.example.reminders.remindershome

import androidx.lifecycle.*
import com.example.reminders.database.AppDatabase
import com.example.reminders.database.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RemindersHomeViewModel(private val database: AppDatabase) : ViewModel() {
    private val _addSuccess = MutableLiveData<Boolean>()
    val addSuccess: LiveData<Boolean>
        get() = _addSuccess

    val allReminders = database.reminderDao.getAll()

    fun addNewReminder() {
        _addSuccess.value = true
        val reminder = Reminder(0, "testing title", "testing description")
        // TODO make sure this use of threads is correct
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.reminderDao.add(reminder)
            }
        }
    }

    fun finishAdding() {
        _addSuccess.value = false
    }

    fun deleteAll() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.reminderDao.deleteAll()
            }
        }
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
