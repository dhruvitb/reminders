package com.example.reminders.reminderdetail

import androidx.lifecycle.*
import com.example.reminders.database.AppDatabase
import com.example.reminders.database.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReminderDetailViewModel(
    private val database: AppDatabase, private val reminderId: Int
) : ViewModel() {
    var reminder: Reminder? = null

    private val _saveReminderClicked = MutableLiveData<Boolean>()
    val saveReminderClicked: LiveData<Boolean>
        get() = _saveReminderClicked

    private val _navigateHome = MutableLiveData<Boolean>()
    val navigateHome: LiveData<Boolean>
        get() = _navigateHome

    init {
        reminderId.let {
            // TODO make sure this use of threads is correct
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    reminder = database.reminderDao.getReminder(reminderId)
                }
            }
        }
    }

    fun saveReminderClicked() {
        _saveReminderClicked.value = true
    }

    fun saveReminder(newReminder: Reminder) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (newReminder.id != 0) database.reminderDao.update(newReminder)
                else database.reminderDao.add(newReminder)
            }
            _saveReminderClicked.value = false
            _navigateHome.value = true
        }
    }

    fun deleteReminder() {
        reminderId.let {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    database.reminderDao.delete(reminderId)
                }
                _navigateHome.value = true
            }
        }
    }

    fun discardReminderClicked() {
        _navigateHome.value = true
    }

    fun finishNavigating() {
        _navigateHome.value = false
    }
}

class RemindersDetailViewModelFactory(
    private val database: AppDatabase, private val reminderId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(ReminderDetailViewModel::class.java)) {
            return ReminderDetailViewModel(database, reminderId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
