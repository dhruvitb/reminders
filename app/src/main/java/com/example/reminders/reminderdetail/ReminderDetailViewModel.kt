package com.example.reminders.reminderdetail

import androidx.lifecycle.*
import com.example.reminders.database.AppDatabase
import com.example.reminders.database.Reminder
import kotlinx.coroutines.launch

class ReminderDetailViewModel(
    private val database: AppDatabase, private val reminderId: Int
) : ViewModel() {
    private val _reminder = MutableLiveData(Reminder(0, "", ""))
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

    init {
        reminderId.let {
            viewModelScope.launch {
                _reminder.value = database.reminderDao.getReminder(reminderId)
            }
        }
    }

    fun saveReminderClicked() {
        _saveReminderClicked.value = true
    }

    fun saveReminder(newReminder: Reminder) {
        var newReminderId= newReminder.id
        viewModelScope.launch {
            if (newReminder.id != 0) {
                database.reminderDao.update(newReminder)
            } else {
                newReminderId = database.reminderDao.add(newReminder).toInt()
            }
            _savedReminder.postValue(
                Reminder(
                    newReminderId,
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
        reminderId.let {
            viewModelScope.launch {
                database.reminderDao.delete(reminderId)
                _navigateHome.value = true
                _removeNotification.value = reminderId
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
