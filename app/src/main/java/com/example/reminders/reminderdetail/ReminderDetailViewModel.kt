package com.example.reminders.reminderdetail

import androidx.lifecycle.*
import com.example.reminders.database.AppDatabase
import com.example.reminders.database.Reminder
import kotlinx.coroutines.launch

class ReminderDetailViewModel(
    private val database: AppDatabase, initialReminder: Reminder
) : ViewModel() {
    private val _reminder = MutableLiveData(initialReminder)
    val reminder: LiveData<Reminder>
        get() = _reminder

    private val _saveReminderClicked = MutableLiveData<Boolean>()
    val saveReminderClicked: LiveData<Boolean>
        get() = _saveReminderClicked

    private val _navigateHome = MutableLiveData<Boolean>()
    val navigateHome: LiveData<Boolean>
        get() = _navigateHome

    private val _savedReminder = MutableLiveData<Reminder>()
    val savedReminder: LiveData<Reminder>
        get() = _savedReminder

    private val _removeNotification = MutableLiveData<Int>()
    val removeNotification: LiveData<Int>
        get() = _removeNotification

    fun saveReminderClicked() {
        _saveReminderClicked.value = true
    }

    fun saveReminder(newReminder: Reminder) {
        var savedId = newReminder.id
        if (newReminder.title.isBlank()) {
            _saveReminderClicked.value = false
            return
        }
        viewModelScope.launch {
            if (newReminder.id != 0) database.reminderDao.update(newReminder)
            else savedId = database.reminderDao.add(newReminder).toInt()
            _savedReminder.postValue(
                Reminder(
                    savedId,
                    newReminder.title,
                    newReminder.description
                )
            )
            _saveReminderClicked.value = false
            _navigateHome.value = true
        }
    }

    fun finishSave() {
        _savedReminder.value = null
    }

    fun deleteReminder() {
        viewModelScope.launch {
            database.reminderDao.delete(_reminder.value!!.id)
            _navigateHome.value = true
            _removeNotification.value = _reminder.value!!.id
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
    private val database: AppDatabase, private val reminder: Reminder
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(ReminderDetailViewModel::class.java)) {
            return ReminderDetailViewModel(database, reminder) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
